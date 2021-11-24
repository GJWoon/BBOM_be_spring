package com.laonstory.bbom.domain.user.domain;


import com.laonstory.bbom.domain.follow.domain.Follow;
import com.laonstory.bbom.domain.user.dto.UserResisterDto;
import com.laonstory.bbom.global.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String email;

    private String password;

    private String profileImage;

    private double height;

    private double weight;

    private String gender;

    private String intro;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    private final List<Follow> follows = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role")
    @Builder.Default
    private final List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

     public static User create(UserResisterDto dto,String password){

        return User.builder()
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .gender(dto.getGender())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .password(password)
                .intro(dto.getIntro())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
     }

     public void addImage(String imagePath){

        this.profileImage = imagePath;
     }

}
