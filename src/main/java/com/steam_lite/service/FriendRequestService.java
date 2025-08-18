package com.steam_lite.service;


import com.steam_lite.domain.friendRequest.FriendRequest;
import com.steam_lite.domain.friendRequest.FriendRequestStatus;
import com.steam_lite.domain.user.User;
import com.steam_lite.dto.user.FriendRequestDto;
import com.steam_lite.exception.FriendRequestException;
import com.steam_lite.repository.FriendRequestRepository;
import com.steam_lite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }


    public void sendFriendRequest(Long fromUserId, Long toUserId) {
        // 친구 요청 대상이 자신인지 확인
        if (fromUserId.equals(toUserId)) {
            throw FriendRequestException.selfRequest();
        }

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("보내는 사용자 없음"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("받는 사용자 없음"));

        // 이미 친구 요청을 보냈는지 확인
        boolean alreadyRequested = friendRequestRepository.findAll().stream().anyMatch(fr ->
                fr.getFromUser().getId().equals(fromUserId) &&
                fr.getToUser().getId().equals(toUserId) &&
                fr.getStatus() == FriendRequestStatus.PENDING);
        if (alreadyRequested) {
            throw FriendRequestException.alreadyRequested();
        }

        // 이미 친구인지 확인
        boolean alreadyFriends = friendRequestRepository.findAll().stream().anyMatch(fr ->
                ((fr.getFromUser().getId().equals(fromUserId) && fr.getToUser().getId().equals(toUserId)) ||
                        (fr.getFromUser().getId().equals(toUserId) && fr.getToUser().getId().equals(fromUserId))) &&
                        fr.getStatus() == FriendRequestStatus.ACCEPTED);
        if (alreadyFriends) {
            throw FriendRequestException.alreadyFriends();
        }

        FriendRequest request = new FriendRequest();
        request.setFromUser(fromUser);
        request.setToUser(toUser);
        request.setStatus(FriendRequestStatus.PENDING);

        friendRequestRepository.save(request);
    }


    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("요청 없음"));
        request.setStatus(FriendRequestStatus.ACCEPTED);

        // 반대 방향 요청 삭제
        friendRequestRepository.findAll().stream().filter(fr ->
                fr.getFromUser().getId().equals(request.getToUser().getId()) &&
                fr.getToUser().getId().equals(request.getFromUser().getId()) &&
                fr.getStatus() == FriendRequestStatus.PENDING
        ).forEach(friendRequestRepository::delete);
    }


    public void rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("요청 없음"));
        request.setStatus(FriendRequestStatus.REJECTED);

        // 반대 방향 요청 삭제
        friendRequestRepository.findAll().stream().filter(fr ->
                fr.getFromUser().getId().equals(request.getToUser().getId()) &&
                fr.getToUser().getId().equals(request.getFromUser().getId()) &&
                fr.getStatus() == FriendRequestStatus.PENDING
        ).forEach(friendRequestRepository::delete);
    }


    public List<FriendRequestDto> getFriends(Long userId) {
        return friendRequestRepository
                .findByFromUserIdAndStatusOrToUserIdAndStatus(
                        userId, FriendRequestStatus.ACCEPTED,
                        userId, FriendRequestStatus.ACCEPTED
                )
                .stream()
                .map(fr -> new FriendRequestDto(
                        fr.getId(),
                        fr.getFromUser().getId(),
                        fr.getFromUser().getUsername(),
                        fr.getToUser().getId(),
                        fr.getToUser().getUsername(),
                        fr.getStatus()
                ))
                .toList();
    }



    public List<FriendRequestDto> getReceivedFriendRequests(Long userId) {
        return friendRequestRepository
                .findByToUserIdAndStatus(userId, FriendRequestStatus.PENDING)
                .stream()
                .map(fr -> new FriendRequestDto(
                        fr.getId(),
                        fr.getFromUser().getId(),
                        fr.getFromUser().getUsername(),
                        fr.getToUser().getId(),
                        fr.getToUser().getUsername(),
                        fr.getStatus()
                ))
                .toList();
    }

    public List<FriendRequestDto> getSentFriendRequests(Long userId) {
        return friendRequestRepository
                .findByFromUserIdAndStatus(userId, FriendRequestStatus.PENDING)
                .stream()
                .map(fr -> new FriendRequestDto(
                        fr.getId(),
                        fr.getFromUser().getId(),
                        fr.getFromUser().getUsername(),
                        fr.getToUser().getId(),
                        fr.getToUser().getUsername(),
                        fr.getStatus()
                ))
                .toList();
    }


    public void deleteFriend(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }
}

