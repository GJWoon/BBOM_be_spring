package com.laonstory.bbom.domain.content.repository;


import com.laonstory.bbom.domain.content.domain.ClothesInfo;
import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.dto.*;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laonstory.bbom.domain.content.domain.QContent.content1;
import static com.laonstory.bbom.domain.content.domain.QContentImage.contentImage;
import static com.laonstory.bbom.domain.content.domain.QComment.comment;
import static com.laonstory.bbom.domain.content.domain.QClothesInfo.clothesInfo;
import static com.laonstory.bbom.domain.content.domain.QContentTag.contentTag;
import static com.laonstory.bbom.domain.content.domain.QContentLike.contentLike;
import static com.laonstory.bbom.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class ContentRepositorySupport {


    private final JPAQueryFactory queryFactory;


    public Page<ContentResponse> findAllByQueryAndPaging(Pageable pageable, String query, User currentUser, ContentSearchModel searchModel) {

        QueryResults<ContentResponse> result = queryFactory
                .select(Projections.constructor(ContentResponse.class, content1, user))
                .from(content1)
                .innerJoin(user)
                .on(user.id.eq(currentUser.getId()))
                .orderBy(content1.createdDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());

    }


    public Page<ContentResponse> findAllByQueryAndPaging(Pageable pageable, String query, ContentSearchModel searchModel) {

        QueryResults<ContentResponse> result = queryFactory
                .select(Projections.constructor(ContentResponse.class, content1))
                .from(content1)
                .orderBy(content1.createdDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());

    }

    private BooleanExpression queryTag(String query) {

        if (query == null) {
            return null;

        }
        return content1.tagList.any().tag.contains(query);
    }


    private BooleanExpression querySearchModel(ContentSearchModel searchModel) {


        return user.weight.between(Double.parseDouble(searchModel.getWeight()), Double.parseDouble(searchModel.getHeight()));

    }

    public ContentDetailResponse findByIdContentResponse(Long id, User user) {

        Content result = queryFactory
                .selectFrom(content1)
                .where(content1.id.eq(id))
                .fetchOne();
        if (result == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ITEM);
        }
        if (user == null) {
            return new ContentDetailResponse(result);
        }
        return new ContentDetailResponse(result, user);
    }


    public long count(Long id) {
        return queryFactory
                .selectFrom(content1)
                .where(content1.user.id.eq(id))
                .fetchCount();

    }


    public Page<ContentMyPageResponse> findByUserIdLimit9(Long userId, Pageable pageable) {

        QueryResults<ContentMyPageResponse> result = queryFactory
                .select(Projections.constructor(ContentMyPageResponse.class, content1))
                .from(content1)
                .where(content1.user.id.eq(userId))
                .orderBy(content1.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(9)
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }


    public Page<ContentMyPageResponse> findByUserIdLimit9(String query, Pageable pageable) {

        QueryResults<ContentMyPageResponse> result = queryFactory
                .select(Projections.constructor(ContentMyPageResponse.class, content1))
                .from(content1)
                .where(content1.id.isNotNull(),queryTag(query))
                .orderBy(content1.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(10)
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }


    public List<Content> findAllOrderByLikeCountLimit5() {
        return queryFactory

                .selectFrom(content1).where(content1.clothesInfos.isNotEmpty())
                .orderBy(content1.like.size().desc())
                .limit(5)
                .fetch();
    }
}
