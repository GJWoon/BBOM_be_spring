package com.laonstory.bbom.domain.content.repository;


import com.laonstory.bbom.domain.content.dto.CommentResponse;
import com.laonstory.bbom.domain.user.domain.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.laonstory.bbom.domain.content.domain.QComment.comment;
@Repository
@RequiredArgsConstructor
public class CommentRepositorySupport {


    private final JPAQueryFactory queryFactory;


    public Page<CommentResponse> findAllPaging(Pageable pageable, Long contentId){

        QueryResults<CommentResponse> result =
                queryFactory
                .select(Projections.constructor(CommentResponse.class,comment))
                .from(comment)
                        .where(comment.content.id.eq(contentId),comment.parent.id.isNull())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(),pageable,result.getTotal());
                        }


}



