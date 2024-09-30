package com.goorm.goormweek2.auth.application;

import com.goorm.goormweek2.auth.application.dto.LoginResponseDto;
import com.goorm.goormweek2.auth.repo.MemberRepository;
import com.goorm.goormweek2.auth.repo.entity.Member;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final TokenService authService;
    private final AuthenticationManager authenticationManager;

    //회원가입
    public void register(String email, String password) {
        String encodedPassword = encoder.encode(password);
        Member member = new Member(email, encodedPassword);
        memberRepository.save(member);
    }

    @Transactional
    public LoginResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new NoSuchElementException("Member with email " + email + " not found"));

        validatePassword(password, member.getPassword());

        Authentication token = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(token);

        return authService.login();
    }

    private void validatePassword(String requestPassword, String savedPassword) {
        if (!encoder.matches(requestPassword, savedPassword)) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public void logout(String token) {
        authService.black(token);
    }
}
