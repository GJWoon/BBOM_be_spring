package com.laonstory.bbom.domain.user.dto;
import com.laonstory.bbom.domain.content.dto.ContentMyPageResponse;
import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
public class UserGetMeResponse extends UserResponse{

    private long contentCount;
    private long followerCount;
    private long followCount;
    private boolean isFollow;

    public UserGetMeResponse(User user, long contentCount, long followerCount, long followCount){
        super(user);
        this.contentCount = contentCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
    }
    public UserGetMeResponse(User user, long contentCount, long followerCount, long followCount,User users){
        super(user);
        this.contentCount = contentCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
        this.isFollow = users != null && user.getFollows().stream().anyMatch(e -> e.getFollower().getId().equals(users.getId()));
    }}
