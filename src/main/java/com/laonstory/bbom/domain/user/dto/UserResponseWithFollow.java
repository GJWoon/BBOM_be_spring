package com.laonstory.bbom.domain.user.dto;


import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserResponseWithFollow extends UserResponse {


    public boolean isFollow;


    public UserResponseWithFollow(User users,User userd) {

        super(users);
        this.isFollow = users.getFollows() != null && users.getFollows().size() != 0 &&
                users
                        .getFollows()
                        .stream()
                        .anyMatch(e -> e.getFollower().getId().equals(userd.getId()));
    }
    public UserResponseWithFollow(User users) {

        super(users);
        this.isFollow = false;
    }


}
