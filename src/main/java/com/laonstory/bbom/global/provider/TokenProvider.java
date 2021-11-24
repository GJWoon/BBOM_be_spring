package com.laonstory.bbom.global.provider;


import com.fasterxml.jackson.databind.JsonNode;
import com.laonstory.bbom.domain.user.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;


    private static final long TOKEN_TIME = 1000L * 60 * 24 * 365;
    private static final long REFRESH_TOKEN_VALID_TIME =   1000L * 60 * 24 * 700;
    private final UserDetailsService userDetailsService;




    // secretKey를 Base64 로 인코딩한다
    @PostConstruct
    protected  void init(){

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    // 토큰을 만들장
    public String createToken(String userId, List<String> roles){



        // Claims 란 jwt 바디 부분
        // 바디부분에 유저에 대한 정보를 넣는다
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles",roles);
        Date now =new Date();


        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+TOKEN_TIME))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }


    public Authentication getAuthentication(String token){

        //token = token.substring(6).trim();

        UserDetails user= userDetailsService.loadUserByUsername(getUserId(token));

        if(user == null){
            throw new EntityNotFoundException();
        }

        return new UsernamePasswordAuthenticationToken(user,"",user.getAuthorities());
    }



    //토큰을 복호화 하고 body에 저장된 유저 id 를 꺼낸다
    public String getUserId(String token){

       // token = token.substring(6).trim();

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

    }


    public String resolveToken(HttpServletRequest req){


        return req.getHeader(HttpHeaders.AUTHORIZATION);
    }


    public boolean validateToken(String token){

      /*  log.info(token);

        token = token.substring(6).trim();

        log.info(token);*/

        Jws<Claims> claimsJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        return !claimsJwt.getBody().getExpiration().before(new Date());

    }

    public String createJwtRefreshToken(String userId,List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles",roles);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


}
