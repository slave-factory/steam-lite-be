package com.steam_lite.dto.user;

import com.steam_lite.domain.friendRequest.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendRequestDto {
    private Long id;
    private Long fromUserId;
    private String fromUserName;
    private Long toUserId;
    private String toUserName;
    private FriendRequestStatus status;
}
