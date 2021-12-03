package com.laonstory.bbom.domain.content.application;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.laonstory.bbom.domain.content.domain.*;
import com.laonstory.bbom.domain.content.dto.*;
import com.laonstory.bbom.domain.content.repository.*;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.domain.user.dto.UserResponseWithFollow;
import com.laonstory.bbom.domain.user.repository.UserRepositorySupport;
import com.laonstory.bbom.global.config.FireBase;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import com.laonstory.bbom.global.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;
    private final ClothesInfoRepository clothesInfoRepository;
    private final ClothesInfoImageRepository clothesInfoImageRepository;
    private final ContentImageRepository contentImageRepository;
    private final ContentTagRepository contentTagRepository;
    private final ContentRepositorySupport contentRepositorySupport;
    private final ContentLikeRepository contentLikeRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final FireBase push;

    public Boolean postContent(List<MultipartFile> infoImages, List<MultipartFile> contentImages, ContentDto dto, User user) {
        Content content = Content.create(dto, user);
        contentRepository.save(content);
        if (infoImages != null && infoImages.size() != 0) {
            List<String> infoImagePathList = ResourceUtil.saveImageList(infoImages, content.getId(), "info");
            for (int i = 0; i < infoImagePathList.size() && i < dto.getInfoList().size(); i++) {
                log.info("아빠사자 !!!!");

                ClothesInfo clothesInfo = ClothesInfo.create(dto.getInfoList().get(i), content);
                ClothesInfoImages clothesInfoImages = new ClothesInfoImages(infoImagePathList.get(i));
                clothesInfoImageRepository.save(clothesInfoImages);
                clothesInfo.addImage(clothesInfoImages);
                content.getClothesInfos().add(clothesInfo);
                clothesInfoRepository.save(clothesInfo);
            }
        }
        // 글 사진 insert
        if (contentImages != null && contentImages.size() != 0) {
            List<String> contentImagePathList = ResourceUtil.saveImageList(contentImages, content.getId(), "content");
            for (int i = 0; i < contentImagePathList.size(); i++) {
                ContentImage contentImage = ContentImage.builder()
                        .content(content)
                        .path(contentImagePathList.get(i))
                        .build();
                contentImageRepository.save(contentImage);
                content.getContentImageList().add(contentImage);
            }
        }
        if (dto.getTagList() != null && dto.getTagList().size() != 0) {
            dto.getTagList().forEach(e -> {
                ContentTag tag = ContentTag.
                        builder()
                        .tag(e)
                        .content(content)
                        .build();
                contentTagRepository.save(tag);
                content.getTagList().add(tag);
            });
        }
        return true;
    }

    public PagingResponse<ContentResponse> findAllPaging(int page, String query, User user, String type, String weight, String height, String size) {
        Pageable pageRequest = new PageRequest(page, 10).of();
        ContentSearchModel searchModel = new ContentSearchModel(size, height, weight, type);
        if (user == null) {
            return new PagingResponse<>(contentRepositorySupport.findAllByQueryAndPaging(pageRequest, query, searchModel));
        }
        return new PagingResponse<>(contentRepositorySupport.findAllByQueryAndPaging(pageRequest, query, user, searchModel));
    }

    public ContentDetailResponse findById(Long id, User user) {
        return contentRepositorySupport.findByIdContentResponse(id, user);
    }

    public Boolean like(Long id, User user) {
        ContentLike contentLike = contentLikeRepository.findByContentIdAndUserId(id, user.getId());
        if (contentLike == null) {
            Content content = contentRepository.findById(id).orElse(null);
            contentLike = ContentLike
                    .builder()
                    .content(content)
                    .user(user)
                    .createdDate(LocalDateTime.now())
                    .build();
            contentLikeRepository.save(contentLike);
            try {
                push.sendMessage(content.getUser().getFcmToken(),user.getNickName()+"님이 게시글에 좋아요를 눌렀어요","뽐으로 확인하러 가볼까요?");
            } catch (IOException | FirebaseMessagingException e) {
                e.printStackTrace();
            }
            return true;
        }
        contentLikeRepository.delete(contentLike);
        return true;
    }

    @Transactional
    public ContentResponseListWithUserResponseList suggestion(User principal) {
        List<User>
                users = userRepositorySupport.suggestionUser();
        Collections.shuffle(users);
        List<UserResponseWithFollow> userResponseList;

        if (principal == null) {
            userResponseList = users.stream().limit(5).map(UserResponseWithFollow::new).collect(Collectors.toList());
        } else {
            User user = userRepositorySupport.findById(principal.getId());
            userResponseList = users.stream().filter(us ->
                    us.getFollows().stream().noneMatch(u -> u.getFollower().getId().equals(user.getId()))
                            && !user.getId().equals(us.getId()))
                    .limit(5).map(e -> new UserResponseWithFollow(e, user)).collect(Collectors.toList());

   /*         userResponseList =  users.stream().filter(
                    u -> user.getFollows().stream()
                            .noneMatch(f -> f.getFollower().getId().equals(u.getId()))
                    && !u.getId().equals(user.getId())
            ).limit(5).map(e-> new UserResponseWithFollow(e,user)).collect(Collectors.toList());*/
        }
        List<ClothesInfoResponse> content = contentRepositorySupport.findAllOrderByLikeCountLimit5().stream().map(e -> new ClothesInfoResponse(e.getClothesInfos().get(0)))
                .collect(Collectors.toList());
        return new ContentResponseListWithUserResponseList(userResponseList, content);
    }

    public PagingResponse<ContentMyPageResponse> findContentMyPageResponse(Long id, int page) {
        Pageable pageable = new PageRequest(page, 12).of();
        return new PagingResponse<>(contentRepositorySupport.findByUserIdLimit9(id, pageable));
    }

    public PagingResponse<ContentMyPageResponse> findContentMyPageResponse(int page, String query) {
        Pageable pageable = new PageRequest(page, 12).of();
        return new PagingResponse<>(contentRepositorySupport.findByUserIdLimit9(query, pageable));
    }

    public Boolean deleteContent(Long id, User user) {
        Content content = contentRepositorySupport.findByIdContentResponse(id);

        if (!content.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.NOT_CONTENT_USER);
        }
        contentRepository.delete(content);
        return true;
    }

    public Boolean updateContent(ContentUpdateDto dto, List<MultipartFile> imageList) {

        Content content = contentRepositorySupport.findByIdContentResponse(dto.getId());
        content.update(dto);
        List<ClothesInfo> deleteInfo = clothesInfoRepository.findAllById(dto.getInfoDeleteId());
        assert deleteInfo.size() != 0;
        clothesInfoRepository.deleteAll(deleteInfo);
        content.getClothesInfos().removeAll(deleteInfo);

        dto.getInfoUpdateList().forEach(e -> {

            ClothesInfo clothesInfo = clothesInfoRepository.findById(e.getId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
            clothesInfo.update(e);
        });
        if (imageList != null && imageList.size() != 0) {
            List<String> infoImagePathList = ResourceUtil.saveImageList(imageList, content.getId(), "info");
            for (int i = 0; i < infoImagePathList.size() && i < dto.getInfoList().size(); i++) {
                ClothesInfo clothesInfo = ClothesInfo.create(dto.getInfoList().get(i), content);
                ClothesInfoImages clothesInfoImages = new ClothesInfoImages(infoImagePathList.get(i));
                clothesInfoImageRepository.save(clothesInfoImages);
                clothesInfo.addImage(clothesInfoImages);
                content.getClothesInfos().add(clothesInfo);
                clothesInfoRepository.save(clothesInfo);
            }
        }
        return true;
    }

    public PagingResponse<ClotheInfoSearchResponse> searchInfo(int page,int limit,String query){
        Pageable pageable = new PageRequest(page,limit).of();
        return new PagingResponse(contentRepositorySupport.searchInfo(pageable,query));
    }
}
