package com.laonstory.bbom.domain.user.repository;

import com.laonstory.bbom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
