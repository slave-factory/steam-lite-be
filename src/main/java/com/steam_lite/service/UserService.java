package com.steam_lite.service;

import com.steam_lite.dto.user.UserLoginRequest;
import com.steam_lite.exception.CustomException;
import com.steam_lite.exception.ErrorCode;
import com.steam_lite.domain.user.User; // User 엔티티 임포트
import com.steam_lite.dto.user.UserSignUpRequest;
import com.steam_lite.dto.user.UserResponse;
import com.steam_lite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // POST /api/signup
    @Transactional
    public UserResponse signUp(UserSignUpRequest request) {
        // 1. 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        // 2. 사용자 이름(username) 중복 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        // 3. DTO를 User 엔티티로 변환하여 생성
        User user = request.toEntity(passwordEncoder);

        // 4. DB에 저장
        User savedUser = userRepository.save(user);

        // 5. 엔티티를 응답 DTO로 변환하여 반환
        return UserResponse.from(savedUser);
    }

    // POST api/login
    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return UserResponse.from(user);
    }

    // GET /api/user/{user_id}
    public UserResponse getUserProfile(Long userId) {
        // user_id로 User 엔티티 조회
        User user = userRepository.findById(userId) // purchasedGames 제거했으므로 findById 사용
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // ----------- 권한 인증/인가 처리 공간 (차후 추가) -----------
        // 비고: 비공개인 유저의 프로필은 일반 유저가 볼수 없음
        // 현재는 구현 범위에서 제외
        // --------------------------------------------------------

        return UserResponse.from(user);
    }

    // 기타 사용자 정보 업데이트/변경 로직은 추후 필요시 Service 내부에 구현
    // 예:
    /*
    @Transactional
    public UserResponse updateUserInfo(Long userId, String newUsername, String newProfileImageUrl, UserStatus newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 필드 변경 로직 (User 엔티티의 @Setter 필요)
        if (newUsername != null && !newUsername.isBlank() && !user.getUsername().equals(newUsername)) {
            if (userRepository.existsByUsername(newUsername)) {
                throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
            }
            user.setUsername(newUsername);
        }
        if (newProfileImageUrl != null) {
            user.setProfileImageUrl(newProfileImageUrl);
        }
        if (newStatus != null) {
            user.setStatus(newStatus);
        }
        return UserResponse.from(user);
    }

    @Transactional
    public void changeUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(newPassword));
    }
    */
}