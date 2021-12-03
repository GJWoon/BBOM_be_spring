package com.laonstory.bbom.global.application;

import com.laonstory.bbom.global.domain.Access;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<Access,Long> {
}
