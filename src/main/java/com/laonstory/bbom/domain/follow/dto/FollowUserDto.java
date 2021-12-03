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

        private double weight;

        private double height;

        private String intro;
        //나를 팔로우 한 사람들]
        public FollowUserDto(Follow follow,String uu,User user){
                this.id = follow.getFollower().getId();
                this.isFollow = follow
                        .getFollower()
                        .getFollows()
                        .stream()
                        .anyMatch(e -> e.getFollower().getId().equals(user.getId()));
                this.profileImage = follow.getFollower().getProfileImage();
                this.weight = follow.getFollower().getWeight();
                this.height = follow.getFollower().getHeight();
                this.intro = follow.getFollower().getIntro();
                this.nickName = follow.getFollower().getNickName();
        }

        public FollowUserDto(Follow follow, User user){
                this.id = follow.getUser().getId();
                this.isFollow = true;
                this.nickName = follow.getUser().getNickName();
                this.profileImage = follow.getUser().getProfileImage();
                this.weight = follow.getUser().getWeight();
                this.height = follow.getUser().getHeight();
                this.intro = follow.getUser().getIntro();
        }

}
