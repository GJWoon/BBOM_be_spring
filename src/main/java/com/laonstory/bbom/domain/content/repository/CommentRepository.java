package com.laonstory.bbom.domain.content.repository;

import com.laonstory.bbom.domain.content.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
