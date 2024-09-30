package com.goorm.goormweek2.auth.repo;

import com.goorm.goormweek2.auth.repo.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
