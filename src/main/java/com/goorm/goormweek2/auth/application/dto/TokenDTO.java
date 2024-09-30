package com.goorm.goormweek2.auth.application.dto;

public record TokenDto(String token) {

    public TokenDto(String token) {
        this.token = token;
    }
}
