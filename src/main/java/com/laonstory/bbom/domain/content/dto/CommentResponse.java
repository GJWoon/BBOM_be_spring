package com.laonstory.bbom.domain.content.dto;


import com.laonstory.bbom.domain.content.domain.Comment;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.domain.user.dto.UserSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;

    private String content;

    private UserSimpleResponse user;

    private LocalDateTime createdDate;
    private boolean isMe;

    //User
    private List<CommentResponse> childComment;

    public CommentResponse(Comment comment, User user) {
        this.id = comment.getId();
        this.content = comment.getCommentContent();
        this.childComment = comment.getChildComment().stream().map(e -> new CommentResponse(e, user)).collect(Collectors.toList());
        this.user = new UserSimpleResponse(comment.getUser());
        this.isMe = user != null && user.getId().equals(comment.getUser().getId());
        this.createdDate = comment.getCreatedDate();
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getCommentContent();
        this.childComment = comment.getChildComment().stream().map(CommentResponse::new).collect(Collectors.toList());
        this.user = new UserSimpleResponse(comment.getUser());
        this.isMe = false;
        this.createdDate = comment.getCreatedDate();
    }

}
