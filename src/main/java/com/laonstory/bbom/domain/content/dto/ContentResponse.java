package com.laonstory.bbom.domain.content.dto;


import com.laonstory.bbom.domain.content.domain.Comment;
import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.domain.ContentTag;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.global.dto.response.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Slf4j
public class ContentResponse {


    private Long id;
    private String content;
    private Boolean isShow;
    private List<String> tagList;
    private List<ImageResponse> contentImage;
    private List<ClothesInfoResponse> infoList;
    private LocalDateTime createdDate;
    private long likeCount;
    private long commentCount;
    private UserResponse userData;
    private boolean isFollow;
    private boolean isLike;
    private List<CommentResponse> comments = new ArrayList<>();
    private boolean isMe;
    public ContentResponse(Content content, User user) {

        this.id = content.getId();
        this.content = content.getContent();
        this.isShow = content.getIsShow();
        this.tagList = content.getTagList().stream().map(ContentTag::getTag).collect(Collectors.toList());
        this.contentImage = content.getContentImageList().stream().map(e -> new ImageResponse(e.getId(), e.getPath())).collect(Collectors.toList());
        this.infoList = content.getClothesInfos().stream().map(ClothesInfoResponse::new).limit(1).collect(Collectors.toList());
        this.likeCount = content.getLike().size();
        this.commentCount = content.getCommentList().size();
        this.isFollow = content.getUser().getFollows() != null && content.getUser().getFollows().size() != 0 && content
                .getUser()
                .getFollows()
                .stream()
                .anyMatch(e -> e.getFollower().getId().equals(user.getId()));
        this.isLike =
                content.getLike() != null && content.getLike().size() != 0 && content.getLike().stream()
                        .anyMatch(e -> e.getUser().getId().equals(user.getId()))
        ;
        this.userData = new UserResponse(content.getUser());
        this.createdDate = content.getCreatedDate();
        List<Comment> comments = content.getCommentList();
        if(comments != null && comments.size() != 0){
            Collections.reverse(comments);
            this.comments = comments.stream().limit(2).map(CommentResponse::new).collect(Collectors.toList());
        }
        this.isMe = content.getUser().getId().equals(user.getId());
    }

    public ContentResponse(Content content) {
        this.id = content.getId();
        this.content = content.getContent();
        this.isShow = content.getIsShow();
        this.tagList = content.getTagList().stream().map(ContentTag::getTag).collect(Collectors.toList());
        this.contentImage = content.getContentImageList().stream().map(e -> new ImageResponse(e.getId(), e.getPath())).collect(Collectors.toList());
        this.infoList = content.getClothesInfos().stream().map(ClothesInfoResponse::new).limit(1).collect(Collectors.toList());
        this.likeCount = content.getLike().size();
        this.commentCount = content.getCommentList().size();
        this.isFollow = false;
        this.isLike = false;
        this.userData = new UserResponse(content.getUser());
        this.createdDate = content.getCreatedDate();
        List<Comment> comments = content.getCommentList();

        if(comments != null && comments.size() != 0){
            Collections.reverse(comments);

            this.comments = comments.stream().limit(2).map(CommentResponse::new).collect(Collectors.toList());
        }
        this.isMe = false;
    }
}
