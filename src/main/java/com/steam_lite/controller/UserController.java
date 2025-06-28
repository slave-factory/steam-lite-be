package com.steam_lite.controller;

import com.steam_lite.dto.user.UserLoginRequest;
import com.steam_lite.dto.user.UserProfileUpdateRequest;
import com.steam_lite.dto.user.UserSignUpRequest;
import com.steam_lite.dto.user.UserResponse;
import com.steam_lite.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
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
        UserResponse response = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}