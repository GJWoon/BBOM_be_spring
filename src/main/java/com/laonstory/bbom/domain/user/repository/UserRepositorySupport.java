package com.laonstory.bbom.domain.user.repository;

import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.global.dto.response.CE;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.laonstory.bbom.domain.follow.domain.QFollow.follow;
import java.util.List;

import static com.laonstory.bbom.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserRepositorySupport {

        private final JPAQueryFactory queryFactory;

        public User findById(Long id){
            User result = queryFactory
                    .selectFrom(user)
                    .where(user.id.eq(id))
                    .fetchOne()
                    ;

            if(result ==null){
                throw new BusinessException(ErrorCode.NOT_FOUND_USER);
            }
            return result;
        }

        public User findByEmail(String email){
            User result = queryFactory
                    .selectFrom(user)
                    .where(user.email.eq(email))
                    .fetchOne();
            if(result  == null){

                throw new BusinessException(ErrorCode.NOT_FOUND_EMAIL);
            }
            return result;
        }

        public long duplicateEmail(String email){

            return queryFactory
                    .selectFrom(user)
                    .where(user.email.eq(email))
                    .fetchCount();



        }

    public long duplicateNickName(String nickName){
        return queryFactory
                .selectFrom(user)
                .where(user.nickName.eq(nickName))
                .fetchCount();
    }

    public Page<UserResponse> findAllUser(Pageable pageable){
           QueryResults<UserResponse> users =  queryFactory
                    .select(Projections.constructor(UserResponse.class,user))
                    .from(user)
                    .where(user.roles.any().eq("ROLE_USER"))
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchResults();

           return new PageImpl<>(users.getResults(),pageable, users.getTotal()) ;}

    public List<User> suggestionUser(){

            return  queryFactory
                    .selectFrom(user)
                    .orderBy(user.follows.size().desc())
                    .limit(100)
                    .distinct()
                    .fetch();
    }
}
