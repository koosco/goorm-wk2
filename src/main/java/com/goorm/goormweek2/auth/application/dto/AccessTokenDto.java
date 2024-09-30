package com.goorm.goormweek2.auth.application.dto;

public record AccessTokenDto(String token) {

    public AccessTokenDto(String token) {
        this.token = "Bearer " + token;
    }
}
