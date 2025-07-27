package com.steam_lite.controller;

import com.steam_lite.service.FriendRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{user_id}")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/friend/{friend_id}")
    public void sendFriendRequest(@PathVariable("user_id") Long fromUserId, @PathVariable("friend_id") Long toUserId) {
        friendRequestService.sendFriendRequest(fromUserId, toUserId);
    }

    @PostMapping("/friend/{request_id}/accept")
    public void acceptFriendRequest(@PathVariable("user_id") Long userId, @PathVariable("request_id") Long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
    }

    @PostMapping("/friend/{request_id}/reject")
    public void rejectFriendRequest(@PathVariable("user_id") Long userId, @PathVariable("request_id") Long requestId) {
        friendRequestService.rejectFriendRequest(requestId);
    }

    @GetMapping("/friend")
    public List<?> getFriends(@PathVariable("user_id") Long userId) {
        return friendRequestService.getFriends(userId);
    }

    @GetMapping("/friend_request")
    public List<?> getReceivedFriendRequests(@PathVariable("user_id") Long userId) {
        return friendRequestService.getReceivedFriendRequests(userId);
    }

    @DeleteMapping("/friend/{friend_id}")
    public void deleteFriend(@PathVariable("user_id") Long userId, @PathVariable("friend_id") Long requestId) {
        friendRequestService.deleteFriend(requestId);
    }

}
