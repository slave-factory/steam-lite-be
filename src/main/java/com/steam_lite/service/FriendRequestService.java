package com.steam_lite.service;


import com.steam_lite.domain.friendRequest.FriendRequest;
import com.steam_lite.domain.friendRequest.FriendRequestStatus;
import com.steam_lite.domain.user.User;
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
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("보내는 사용자 없음"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("받는 사용자 없음"));

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
    }


    public void rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("요청 없음"));
        request.setStatus(FriendRequestStatus.REJECTED);
    }


    public List<FriendRequest> getFriends(Long userId) {
        return friendRequestRepository.findAll().stream()
                .filter(fr -> (fr.getFromUser().getId().equals(userId) || fr.getToUser().getId().equals(userId))
                        && fr.getStatus() == FriendRequestStatus.ACCEPTED)
                .toList();
    }


    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        return friendRequestRepository.findAll().stream()
                .filter(fr -> fr.getToUser().getId().equals(userId) && fr.getStatus() == FriendRequestStatus.PENDING)
                .toList();
    }


    public void deleteFriend(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }
}

