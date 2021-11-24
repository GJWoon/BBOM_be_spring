package com.laonstory.bbom.domain.follow.dto;


import com.laonstory.bbom.domain.follow.domain.Follow;
import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserDto {

        private Long id;

        private boolean isFollow;

        private String nickName;

        private String profileImage;


        //나를 팔로우 한 사람들
        public FollowUserDto(Follow follow){

                this.id = follow.getFollower().getId();
                this.isFollow = true;
                this.nickName = follow.getFollower().getNickName();
                this.profileImage = follow.getFollower().getProfileImage();

        }

        //내가 한 사람들
        public FollowUserDto(Follow follow, User user){
                this.id = follow.getUser().getId();
                this.isFollow = follow.getUser().getFollows().stream().anyMatch(e-> e.getUser().getId().equals(user.getId()));
                this.nickName = follow.getUser().getNickName();
                this.profileImage = follow.getUser().getProfileImage();
        }

}
