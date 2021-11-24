package com.laonstory.bbom.domain.user.dto;


import com.laonstory.bbom.domain.content.dto.ContentMyPageResponse;
import com.laonstory.bbom.domain.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

        @ApiModelProperty
        private Long id;
        private String nickName;
        private String profileImage;
        private long contentCount;
        private long followerCount;
        private long followCount;
        private List<ContentMyPageResponse> contentList;
        private LocalDateTime createdDate;

        public UserDetailResponse(User user,long contentCount,long followerCount,long followCount,List<ContentMyPageResponse> list){
                this.id = user.getId();
                this.nickName = user.getNickName();
                this.profileImage = user.getProfileImage();
                this.contentCount = contentCount;
                this.followerCount = followerCount;
                this.followCount = followCount;
                this.contentList = list;
                this.createdDate = user.getCreatedDate();
        }

}
