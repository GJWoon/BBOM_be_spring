package com.laonstory.bbom.domain.content.dto;


import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.domain.user.dto.UserResponseWithFollow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseListWithUserResponseList {

    public List<UserResponseWithFollow> userList;
    public List<ClothesInfoResponse> contentList;

}
