package com.laonstory.bbom.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResisterDto {

    private String nickName;

    private String email;

    private String password;

    private double weight;

    private double height;

    private String gender;

    private String intro;
}
