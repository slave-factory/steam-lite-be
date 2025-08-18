package com.steam_lite.repository;

import com.steam_lite.domain.friendRequest.FriendRequest;
import com.steam_lite.domain.friendRequest.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByToUserIdAndStatus(Long toUserId, FriendRequestStatus status);

    List<FriendRequest> findByFromUserIdAndStatus(Long fromUserId, FriendRequestStatus status);

    List<FriendRequest> findByFromUserIdAndStatusOrToUserIdAndStatus(
            Long fromUserId, FriendRequestStatus status1,
            Long toUserId, FriendRequestStatus status2
    );
}
