package com.steam_lite.controller;

import com.steam_lite.dto.user.FriendRequestDto;
import com.steam_lite.service.FriendRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{user_id}/friend")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    // 친구 요청
    @PostMapping("/{friend_id}")
    public void sendFriendRequest(@PathVariable("user_id") Long fromUserId, @PathVariable("friend_id") Long toUserId) {
        friendRequestService.sendFriendRequest(fromUserId, toUserId);
    }

    // 친구 요청 수락
    @PostMapping("/{request_id}/accept")
    public void acceptFriendRequest(@PathVariable("user_id") Long userId, @PathVariable("request_id") Long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
    }

    // 친구 요청 거절
    @PostMapping("/{request_id}/reject")
    public void rejectFriendRequest(@PathVariable("user_id") Long userId, @PathVariable("request_id") Long requestId) {
        friendRequestService.rejectFriendRequest(requestId);
    }

    // 친구 목록 조회
    @GetMapping
    public List<FriendRequestDto> getFriends(@PathVariable("user_id") Long userId) {
        return friendRequestService.getFriends(userId);
    }

    // 받은 친구 요청 조회
    @GetMapping("/requests/received")
    public List<FriendRequestDto> getReceivedFriendRequests(@PathVariable("user_id") Long userId) {
        return friendRequestService.getReceivedFriendRequests(userId);
    }

    // 보낸 친구 요청 조회
    @GetMapping("/requests/sent")
    public List<FriendRequestDto> getSentFriendRequests(@PathVariable("user_id") Long userId) {
        return friendRequestService.getSentFriendRequests(userId);
    }

    // 친구 삭제
    @DeleteMapping("/{friend_id}")
    public void deleteFriend(@PathVariable("user_id") Long userId, @PathVariable("friend_id") Long requestId) {
        friendRequestService.deleteFriend(requestId);
    }

}
