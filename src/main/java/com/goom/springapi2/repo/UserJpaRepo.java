package com.goom.springapi2.repo;

import com.goom.springapi2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {

}
