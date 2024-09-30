package com.goorm.goormweek2.auth.repo;

import com.goorm.goormweek2.auth.repo.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
