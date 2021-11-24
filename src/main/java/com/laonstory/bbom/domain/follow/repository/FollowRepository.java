package com.laonstory.bbom.domain.follow.repository;

import com.laonstory.bbom.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {
}
