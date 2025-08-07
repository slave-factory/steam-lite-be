package com.steam_lite.repository;

import com.steam_lite.domain.friendRequest.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
