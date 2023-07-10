package com.lahee.todo.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode {
    INVALID_JWT_AUTHORIZATION(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 서명입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 서명입니다."),
    INVALID_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 서명입니다."),
    NO_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "JWT 서명이 존재하지 않습니다."),
    NO_AUTHORIZATION_TOKEN(HttpStatus.FORBIDDEN, "권한이 맞지 않습니다."),
    USER_INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 Refresh Token 입니다."),
    USER_INVALID_USER_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "토큰의 정보가 유저 정보가 일치하지 않습니다."),
    USER_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "로그아웃 된 사용자입니다.");

    private final HttpStatus status;
    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
