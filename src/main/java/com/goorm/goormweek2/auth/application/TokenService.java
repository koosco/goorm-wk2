package com.goorm.goormweek2.auth.application;

import com.goorm.goormweek2.auth.application.dto.AccessTokenDto;
import com.goorm.goormweek2.auth.application.dto.TokenDto;
import com.goorm.goormweek2.auth.application.dto.LoginResponseDto;
import com.goorm.goormweek2.auth.repo.BlackListRepository;
import com.goorm.goormweek2.auth.repo.RefreshTokenRepository;
import com.goorm.goormweek2.auth.repo.entity.BlackList;
import com.goorm.goormweek2.auth.repo.entity.RefreshToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Access;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static SecretKey key;

    private final static long accessTokenValidity = 86400000;

    @Value("${spring.security.secret}")
    private String secret;

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListRepository blackListRepository;

    @PostConstruct
    void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public LoginResponseDto login() {
        AccessTokenDto accessToken = generateAccessToken();
        TokenDto refreshToken = generateRefreshToken();
        return new LoginResponseDto(accessToken.token(), refreshToken.token());
    }

    private AccessTokenDto generateAccessToken() {
        long now = (new Date()).getTime();

        String token = Jwts.builder()
            .signWith(key)
            .setSubject("hi")
            .setExpiration(new Date(now + accessTokenValidity))
            .setIssuedAt(new Date())
            .compact();
        return new AccessTokenDto(token);
    }

    private TokenDto generateRefreshToken() {

        String token = Jwts.builder()
            .signWith(key)
            .setSubject("hi")
            .compact();

        RefreshToken refreshToken = new RefreshToken(token);
        refreshTokenRepository.save(refreshToken);

        return new TokenDto(token);
    }

    //액세스 토큰과 리프레시 토큰 함께 재발행
    public AccessTokenDto reissueToken(String refreshToken) {
        refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new ExpiredJwtException(null, null, "Token has expired"));
        return generateAccessToken();
    }

    public void black(String token) {
        BlackList entity = new BlackList(token);
        blackListRepository.save(entity);
    }
}
