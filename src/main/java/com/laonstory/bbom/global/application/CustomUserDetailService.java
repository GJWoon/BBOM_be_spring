package com.laonstory.bbom.global.application;


import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService{


        private final UserJpaRepository userJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userJpaRepository.findById(Long.valueOf(userId)).orElse(null);

        return user;

    }
}
