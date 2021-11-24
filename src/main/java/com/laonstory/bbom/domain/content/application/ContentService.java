package com.laonstory.bbom.domain.content.application;


import com.laonstory.bbom.domain.content.domain.*;
import com.laonstory.bbom.domain.content.dto.*;
import com.laonstory.bbom.domain.content.repository.*;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.domain.user.dto.UserResponseWithFollow;
import com.laonstory.bbom.domain.user.repository.UserRepositorySupport;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public Boolean postContent(List<MultipartFile> infoImages, List<MultipartFile> contentImages, ContentDto dto,User user) {
        Content content = Content.create(dto,user);
        contentRepository.save(content);
        if (infoImages != null && infoImages.size() != 0) {
            List<String> infoImagePathList = ResourceUtil.saveImageList(infoImages, content.getId(), "info");
            for (int i = 0; i < infoImagePathList.size() && i < dto.getInfoList().size(); i++) {
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
    public PagingResponse<ContentResponse> findAllPaging(int page, String query, User user,String type,String weight,String height,String size) {
        Pageable pageRequest = new PageRequest(page, 10).of();
        ContentSearchModel searchModel = new ContentSearchModel(size,height,weight,type);
        if(user == null){
            return new PagingResponse<>(contentRepositorySupport.findAllByQueryAndPaging(pageRequest, query,searchModel));
        }
        return new PagingResponse<>(contentRepositorySupport.findAllByQueryAndPaging(pageRequest, query, user,searchModel));
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
                    .build();
            contentLikeRepository.save(contentLike);
            return true;
        }
        contentLikeRepository.delete(contentLike);
        return true;
    }
    @Transactional
    public ContentResponseListWithUserResponseList suggestion(User principal){
        List<User>
             users =  userRepositorySupport.suggestionUser();
        Collections.shuffle(users);
        List<UserResponseWithFollow> userResponseList;

        if(principal == null){
            userResponseList =  users.stream().limit(5).map(UserResponseWithFollow::new).collect(Collectors.toList());
        }else{
            User user = userRepositorySupport.findById(principal.getId());
            userResponseList = users.stream().filter(us ->
                    us.getFollows().stream().noneMatch(u -> u.getFollower().getId().equals(user.getId()))
                            && !user.getId().equals(us.getId()))
                    .limit(5).map(e-> new UserResponseWithFollow(e,user)).collect(Collectors.toList());

   /*         userResponseList =  users.stream().filter(
                    u -> user.getFollows().stream()
                            .noneMatch(f -> f.getFollower().getId().equals(u.getId()))
                    && !u.getId().equals(user.getId())
            ).limit(5).map(e-> new UserResponseWithFollow(e,user)).collect(Collectors.toList());*/
        }
        List<ClothesInfoResponse> content = contentRepositorySupport.findAllOrderByLikeCountLimit5().stream().map(e-> new ClothesInfoResponse(e.getClothesInfos().get(0)))
                .collect(Collectors.toList());
        return new ContentResponseListWithUserResponseList(userResponseList,content);
    }
    public PagingResponse<ContentMyPageResponse> findContentMyPageResponse(Long id,int page){
        Pageable pageable = new PageRequest(page,9).of();
        return new PagingResponse<>(contentRepositorySupport.findByUserIdLimit9(id,pageable));
    }
    public PagingResponse<ContentMyPageResponse> findContentMyPageResponse(int page,String query){
        Pageable pageable = new PageRequest(page,9).of();
        return new PagingResponse<>(contentRepositorySupport.findByUserIdLimit9(query,pageable));
    }
}
