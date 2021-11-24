package com.laonstory.bbom.domain.user.dto;

import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {

    private Long id;
    private String nickName;
    private String email;
    private String profileImage;
    private String intro;

    public UserSimpleResponse(User user){
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.intro = user.getIntro();
    }
}
