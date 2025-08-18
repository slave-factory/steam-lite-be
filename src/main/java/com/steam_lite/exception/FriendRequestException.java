package com.steam_lite.exception;

public class FriendRequestException extends RuntimeException {

    private FriendRequestException(String message) {
        super(message);
    }

    public static FriendRequestException selfRequest() {
        return new FriendRequestException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
    }

    public static FriendRequestException alreadyRequested() {
        return new FriendRequestException("이미 친구 요청을 보낸 상태입니다.");
    }

    public static FriendRequestException alreadyFriends() {
        return new FriendRequestException("이미 친구 상태입니다.");
    }
}


