package com.goorm.goormweek2.auth.repo;

import com.goorm.goormweek2.auth.repo.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {

}
