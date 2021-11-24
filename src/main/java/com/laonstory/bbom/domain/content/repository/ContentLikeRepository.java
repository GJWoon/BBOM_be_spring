package com.laonstory.bbom.domain.content.repository;


import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.domain.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentLikeRepository extends JpaRepository<ContentLike,Long> {

    ContentLike findByContentIdAndUserId(Long contentId,Long userId);
}
