package com.goorm.goormweek2.auth.repo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "spring:security:token:refresh")
public class RefreshToken {

    @Id
    private String token;
}
