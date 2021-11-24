package com.laonstory.bbom.domain.user.dto;


import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TokenResponse extends UserResponse{

    public String token;
    public String refreshToken;
    public TokenResponse(String token,String reToken, User user){
        super(user);
        this.token = token;
        this.refreshToken = reToken;
    }
}
