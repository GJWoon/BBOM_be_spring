package com.laonstory.bbom.domain.follow.repository;


import com.laonstory.bbom.domain.follow.domain.Follow;
import com.laonstory.bbom.domain.follow.dto.FollowUserDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.laonstory.bbom.domain.follow.domain.QFollow.follow;
import static com.laonstory.bbom.domain.user.domain.QUser.user;
@Repository
@RequiredArgsConstructor
public class FollowRepositorySupport {

        private final JPAQueryFactory queryFactory;

        public Follow findByUserIdAndContentId(Long userId,Long followerId){

                return queryFactory
                        .selectFrom(follow)
                        .where(follow.follower.id.eq(userId),follow.user.id.eq(followerId))
                        .fetchOne();
        }

        //나를 팔로우 한 사람들
        public Page<FollowUserDto> findFollowUser(Pageable pageable, Long id){

                QueryResults<FollowUserDto> result =
                        queryFactory
                                .select(Projections.constructor(FollowUserDto.class,follow))
                        .from(follow)
                                .innerJoin(user)
                                .on(user.id.eq(id))
                        .where(follow.user.id.eq(id))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

                return new PageImpl<>(result.getResults(),pageable,result.getTotal());

        }



        public Page<FollowUserDto> findMeFollowingUser(Pageable pageable, Long id){

                QueryResults<FollowUserDto> result =
                        queryFactory
                                .select(Projections.constructor(FollowUserDto.class,follow,user))
                                .from(follow)
                                .innerJoin(user)
                                .on(user.id.eq(id))
                                .where(follow.follower.id.eq(id))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetchResults();

                return new PageImpl<>(result.getResults(),pageable,result.getTotal());
        }



        // 내 팔로워들이 몇명인지
        public long findFollowingCount(Long id){
                return
                        queryFactory
                                .selectFrom(follow)
                                .where(follow.user.id.eq(id))
                                .fetchCount();

        }

        //내가 팔로우한 사람들이 몇명인지
        public long findMeFollowingUserCount(Long id){
               return
                        queryFactory
                                                .selectFrom(follow)
                                .where(follow.follower.id.eq(id))
                        .fetchCount();

        }

}
