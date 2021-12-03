package com.laonstory.bbom.domain.user.Application;


import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.domain.ContentLike;
import com.laonstory.bbom.domain.content.dto.ContentMyPageResponse;
import com.laonstory.bbom.domain.content.repository.ContentRepositorySupport;
import com.laonstory.bbom.domain.follow.domain.Follow;
import com.laonstory.bbom.domain.follow.repository.FollowRepositorySupport;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.*;
import com.laonstory.bbom.domain.user.repository.UserJpaRepository;
import com.laonstory.bbom.domain.user.repository.UserRepositorySupport;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import com.laonstory.bbom.global.provider.TokenProvider;
import com.laonstory.bbom.global.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
        return new TokenResponse(token, refreshToken, user);
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

    public UserGetMeResponse findById(Long id, User users) {
        User user = userRepositorySupport.findById(id);
        long followerCount = followRepositorySupport.findFollowingCount(user.getId());
        long followCount = followRepositorySupport.findMeFollowingUserCount(user.getId());
        long contentCount = contentRepositorySupport.count(id);
        return new UserGetMeResponse(user, contentCount, followerCount, followCount, users);
    }

    public UserGetMeResponse findMe(User user) {
        User reUser = userRepositorySupport.findById(user.getId());
        long followerCount = followRepositorySupport.findFollowingCount(user.getId());
        long followCount = followRepositorySupport.findMeFollowingUserCount(user.getId());
        long contentCount = contentRepositorySupport.count(reUser.getId());
        return new UserGetMeResponse(reUser, contentCount, followerCount, followCount);
    }

    public PagingResponse<UserResponse> findAllUser(int page) {
        Pageable pageable = new PageRequest(page, 10).of();
        return new PagingResponse<>(userRepositorySupport.findAllUser(pageable));
    }

    public Boolean userUpdate(UserUpdateDto dto, MultipartFile image, User principal) throws IOException {
        User user = userRepositorySupport.findById(principal.getId());
        if (image != null) {
            String folderPath = "/images/" + "profile" + "/" + dto.getNickName() + "/";
            String location = folderPath + image.getOriginalFilename();
            ResourceUtil.saveFile(image, location, folderPath);
            user.addImage(location);
        }
        if (dto.getImageDefault()) {
            user.addImage("/images/default/Group 62.png");
        }
        user.update(dto);
        return true;
    }



    public Boolean deleteUser(User user) {
        User deleteUser = userRepositorySupport.findById(user.getId());
        userJpaRepository.delete(deleteUser);
        return true;
    }

    public Boolean updatePassword(UpdatePasswordDto dto, User user) {
        if (passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            User currnetUser = userRepositorySupport.findById(user.getId());
            currnetUser.updatePassword(passwordEncoder.encode(dto.getUpdatePassword()));
        } else throw new BusinessException(ErrorCode.NOT_MATCH_PASSWORD);
        return true;
    }

    public boolean updateFcm(Map<String, String> fcmMap, User user) {
        User cUser = userRepositorySupport.findById(user.getId());
        cUser.updateFcm(fcmMap.get("fcm"));
        return true;
    }

    public List<AlarmResponse> getAlarm(User user) {

        List<Long> findMeFollowIds = followRepositorySupport.findMeFollowIds(user.getId());

        List<Content> contentList = contentRepositorySupport.findFollowContent(findMeFollowIds);
        List<Follow> followList = followRepositorySupport.findAlarmFollow(user.getId());
        List<ContentLike> contentLikeList = contentRepositorySupport.findAlarmContentLike(user.getId());
        List<AlarmResponse> resultObject = new ArrayList<>();
        contentList.forEach(e -> {
            resultObject.add(new AlarmResponse(e));
        });
        followList.forEach(e -> {
            resultObject.add(new AlarmResponse(e));
        });
        contentLikeList.forEach(e -> {
            resultObject.add(new AlarmResponse(e));
        });
        resultObject.sort(Comparator.comparing(AlarmResponse::getCreatedDate));
        Collections.reverse(resultObject);
        return resultObject;
    }

    public PagingResponse<UserResponseWithFollow> search(User user, String query, int page, int limit) {
        Pageable pageable = new PageRequest(page, limit).of();
        Page<User> result = userRepositorySupport.search(query, pageable);
        if (user != null) {
            return new PagingResponse<>(page, result.getTotalPages(), result.getTotalElements(), result.get().map(e -> new UserResponseWithFollow(e, user)).collect(Collectors.toList()));
        }
        return new PagingResponse<>(page, result.getTotalPages(), result.getTotalElements(), result.get().map(UserResponseWithFollow::new).collect(Collectors.toList()));
    }

}
