package com.laonstory.bbom.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String nickName;
    private double weight;
    private double height;
    private String gender;
    private String intro;
    private Boolean imageDefault;
}
