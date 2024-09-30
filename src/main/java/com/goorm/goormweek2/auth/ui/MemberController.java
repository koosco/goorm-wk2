package com.goorm.goormweek2.auth.ui;

import com.goorm.goormweek2.auth.application.MemberService;
import com.goorm.goormweek2.auth.application.TokenService;
import com.goorm.goormweek2.auth.application.dto.AccessTokenDto;
import com.goorm.goormweek2.auth.application.dto.LoginRequestDto;
import com.goorm.goormweek2.auth.application.dto.LoginResponseDto;
import com.goorm.goormweek2.auth.application.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequestDto dto) {
        memberService.register(dto.email(), dto.password());
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto token = memberService.login(dto.email(), dto.password());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenDto> reissue(@RequestBody TokenDto dto) {
        return ResponseEntity.ok(tokenService.reissueToken(dto.token()));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenDto dto) {
        memberService.logout(dto.token());
        return ResponseEntity.ok("로그아웃 성공");
    }
}