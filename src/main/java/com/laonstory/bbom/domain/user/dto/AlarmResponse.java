package com.laonstory.bbom.domain.user.dto;


import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.domain.ContentLike;
import com.laonstory.bbom.domain.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {


    private Long id;
    private String image;
    private String type;
    private String nickName;
    private LocalDateTime createdDate;

    public AlarmResponse(Follow follow){

        this.id = follow.getFollower().getId();
        this.createdDate = follow.getCreatedDate();
        this.type = "FOLLOW";
        this.nickName = follow.getFollower().getNickName();
        this.image = follow.getFollower().getProfileImage();

    }
    public AlarmResponse(Content content){

        this.id = content.getId();
        this.image = content.getContentImageList() != null && content.getContentImageList().size() != 0 ? content.getContentImageList().get(0).getPath() : null;
        this.type = "CONTENT";
        this.nickName = content.getUser().getNickName();
        this.createdDate = content.getCreatedDate();
    }
    public AlarmResponse(ContentLike like){
        this.id = like.getContent().getId();
        this.nickName = like.getUser().getNickName();
        this.image = like.getUser().getProfileImage();
        this.createdDate = like.getCreatedDate();
        this.type = "LIKE";
    }


    public AlarmResponse(Long id) {
    }
}
