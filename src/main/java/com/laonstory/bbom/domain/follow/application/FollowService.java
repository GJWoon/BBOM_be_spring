package com.laonstory.bbom.domain.follow.application;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.laonstory.bbom.domain.follow.domain.Follow;
import com.laonstory.bbom.domain.follow.dto.FollowUserDto;
import com.laonstory.bbom.domain.follow.repository.FollowRepository;
import com.laonstory.bbom.domain.follow.repository.FollowRepositorySupport;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.repository.UserRepositorySupport;
import com.laonstory.bbom.global.config.FireBase;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

        private final FollowRepositorySupport followRepositorySupport;
        private final UserRepositorySupport userRepositorySupport;
        private final FollowRepository followRepository;
        private final FireBase push;

        public Boolean follow(Long id, User user){

            if(user.getId().equals(id)){
                throw new BusinessException(ErrorCode.FOLLOWING_ME);
            }
            Follow follow = followRepositorySupport.findByUserIdAndContentId(user.getId(),id);

            if(follow == null){
               User findUser = userRepositorySupport.findById(id);
                follow = Follow
                        .builder()
                        .follower(user)
                        .user(findUser)
                        .createdDate(LocalDateTime.now())
                .build();
                followRepository.save(follow);
                try {
                    push.sendMessage(findUser.getFcmToken(),user.getNickName() +"님이 팔로우 했어요","뽐으로 확인하러 가볼까요?");
                } catch (IOException | FirebaseMessagingException e) {
                    e.printStackTrace();
                }
                return true;
            }
            followRepository.delete(follow);


            return true;
        }
        public PagingResponse<FollowUserDto> findAllPaging(int page,User user,String type){
            Pageable pageable = new PageRequest(page,20).of();

            if(type.equals("follower")){
                return new PagingResponse<>(followRepositorySupport.findFollowUser(pageable,user.getId()));
            }
            return new PagingResponse<>(followRepositorySupport.findMeFollowingUser(pageable,user.getId()));

        }

        public void test(){
            User findUser = userRepositorySupport.findById(46L);
            User findUser1 = userRepositorySupport.findById(47L);

        for(int i = 0; i < 54835; i++) {
            Follow follow = Follow
                    .builder()
                    .follower(findUser1)
                    .user(findUser)
                    .createdDate(LocalDateTime.now())
                    .build();
            followRepository.save(follow);
        }
        }
}
