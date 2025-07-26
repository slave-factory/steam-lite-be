package com.steam_lite.domain.user;

public enum UserRole {
    GUEST, // 비로그인 사용자
    USER, // 로그인 사용자
    ADMIN; // 관리자

    public String getAuthority(){
        return "ROLE_" + this.name();
    }
}