package com.goorm.goormweek2.auth.application;

import com.goorm.goormweek2.auth.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[CustomUserDetailsService] loadUserByUsername called");
        log.info("[CustomUserDetailsService] email = {}", email);
        return memberRepository.findByEmail(email)
            .orElseThrow();
    }
}
