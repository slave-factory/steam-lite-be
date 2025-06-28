package com.steam_lite.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Method Not Allowed"),
    // 인증/인가 관련 에러 (차후 사용)
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C003", "Access is Denied"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User Not Found"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U002", "Email is already in use"),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U003", "Username is already in use"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U004", "Invalid Password"),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "U005", "Invalid Request Body"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}