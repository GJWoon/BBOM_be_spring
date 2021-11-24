package com.laonstory.bbom.domain.user.Application;


import com.laonstory.bbom.domain.content.dto.ContentMyPageResponse;
import com.laonstory.bbom.domain.content.repository.ContentRepositorySupport;
import com.laonstory.bbom.domain.follow.repository.FollowRepositorySupport;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.*;
import com.laonstory.bbom.domain.user.repository.UserJpaRepository;
import com.laonstory.bbom.domain.user.repository.UserRepositorySupport;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import com.laonstory.bbom.global.provider.TokenProvider;
import com.laonstory.bbom.global.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositorySupport userRepositorySupport;
    private final TokenProvider tokenProvider;
    private final FollowRepositorySupport followRepositorySupport;
    private final ContentRepositorySupport contentRepositorySupport;

    public boolean resisterUser(UserResisterDto dto, MultipartFile image) throws IOException {

        String encodePassword = passwordEncoder.encode(dto.getPassword());

        User user = User.create(dto, encodePassword);

        log.info(dto.getHeight() + "hi");

        String location = "/images/default/Group 62.png";

        if (image != null) {

            String folderPath = "/images/" + "profile" + "/" + dto.getNickName() + "/";

            location = folderPath + image.getOriginalFilename();

            ResourceUtil.saveFile(image, location, folderPath);

        }
        user.addImage(location);

        userJpaRepository.save(user);

        return true;

    }

    public TokenResponse login(LoginDto dto) {
        User user = userRepositorySupport.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        String token = tokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());
        String refreshToken = tokenProvider.createJwtRefreshToken(String.valueOf(user.getId()), user.getRoles());
        return new TokenResponse(token,refreshToken, user);
    }

    public boolean checkEmail(String query) {

        long count = userRepositorySupport.duplicateEmail(query);

        if (count == 0) {
            return true;
        }
        return false;
    }

    public boolean checkNickName(String query) {

        long count = userRepositorySupport.duplicateNickName(query);

        if (count == 0) {
            return true;
        }
        return false;
    }

    public UserGetMeResponse findById(Long id,User users) {
        User user = userRepositorySupport.findById(id);
        long followerCount = followRepositorySupport.findFollowingCount(user.getId());
        long followCount = followRepositorySupport.findMeFollowingUserCount(user.getId());
        long contentCount = contentRepositorySupport.count(id);
        return new UserGetMeResponse(user, contentCount, followerCount, followCount,users);
    }

    public UserGetMeResponse findMe(User user) {
        User reUser = userRepositorySupport.findById(user.getId());
        long followerCount = followRepositorySupport.findFollowingCount(user.getId());
        long followCount = followRepositorySupport.findMeFollowingUserCount(user.getId());
        long contentCount = contentRepositorySupport.count(reUser.getId());
        return new UserGetMeResponse(reUser,contentCount,followerCount,followCount);
    }

    public PagingResponse<UserResponse> findAllUser(int page){
        Pageable pageable = new PageRequest(page,10).of();
        return new PagingResponse<>(userRepositorySupport.findAllUser(pageable));
    }
}
