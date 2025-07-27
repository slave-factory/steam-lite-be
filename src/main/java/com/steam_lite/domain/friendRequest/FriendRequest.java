package com.steam_lite.domain.friendRequest;

import com.steam_lite.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_requests")
@Getter @Setter
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @PrePersist
    public void prePersist() {
        this.requestedAt = LocalDateTime.now();
    }
}
