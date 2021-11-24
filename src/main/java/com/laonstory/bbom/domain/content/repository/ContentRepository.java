package com.laonstory.bbom.domain.content.repository;

import com.laonstory.bbom.domain.content.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content,Long> {
}
