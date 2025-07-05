package com.steam_lite.controller;

import com.steam_lite.dto.user.UserLoginRequest;
import com.steam_lite.dto.user.UserProfileUpdateRequest;
import com.steam_lite.dto.user.UserSignUpRequest;
import com.steam_lite.dto.user.UserResponse;
import com.steam_lite.security.CustomUserDetails;
import com.steam_lite.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/signup") // POST api/signup
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
        UserResponse response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}") // GET api/users/{user_id}
    public ResponseEntity<UserResponse> getUserProfile(
            @PathVariable Long userId) {
        UserResponse response = userService.getUserProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileUpdateRequest request
    ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 사용자
        if(auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Long loginUserId = userDetails.getUser().getId();

        // 로그인한 userId와 프로필 수정 userId가 다른경우
        if(!userId.equals(loginUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse response = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}