package com.steam_lite.controller;

import com.steam_lite.domain.user.User;
import com.steam_lite.dto.user.UserLoginRequest;
import com.steam_lite.dto.user.UserResponse;
import com.steam_lite.security.CustomUserDetails;
import com.steam_lite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request, HttpSession session){
        // 로그인 검증
        UserResponse response = userService.login(request);

        User user = userService.getUserByUsername(request.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken( // SecurityContext에 인증 객체 등록
                new CustomUserDetails(user),
                null,
                List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 세션 테스트용
    @GetMapping("/session-check")
    public ResponseEntity<String> sessionCheck(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(session == null || auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션만료");
        }

        boolean isGuest = auth.getAuthorities().stream().anyMatch(a-> a.getAuthority().equals("ROLE_GUEST"));

        if(isGuest) return ResponseEntity.ok("비로그인 사용자입니다. ROLE = " + auth.getAuthorities().stream().findFirst().get().getAuthority());

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        String returnString = "로그인한 사용자 ID = " + userDetails.getUser().getId() + "\n" + "로그인한 사용자 ROLE = " + userDetails.getAuthorities();

        return ResponseEntity.ok(returnString);
    }
}
