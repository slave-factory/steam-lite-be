package com.steam_lite.controller;

import com.steam_lite.dto.user.UserLoginRequest;
import com.steam_lite.dto.user.UserResponse;
import com.steam_lite.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request, HttpSession session){
        // 로그인 검증
        UserResponse response = userService.login(request);
        // 세션에 로그인된 사용자 ID 저장
        session.setAttribute("LOGIN_USER_ID", response.getUserId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
