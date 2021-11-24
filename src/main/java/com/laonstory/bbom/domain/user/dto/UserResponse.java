package com.laonstory.bbom.domain.user.dto;

import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class UserResponse extends UserSimpleResponse {


    private List<String> roles;
    private double height;
    private double weight;
    private String gender;
    private LocalDateTime createdDate;

    public UserResponse(User user){

        super(user);
        this.roles = user.getRoles();
        this.gender = user.getGender();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.createdDate = user.getCreatedDate();
    }



}
