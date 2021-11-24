package com.laonstory.bbom.domain.content.dto;


import com.laonstory.bbom.domain.content.domain.Comment;
import com.laonstory.bbom.domain.user.dto.UserResponse;
import com.laonstory.bbom.domain.user.dto.UserSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
        private Long id;

        private String content;

        private UserSimpleResponse user;
        //User
        private List<CommentResponse> childComment;
        public CommentResponse(Comment comment){
            this.id = comment.getId();
            this.content = comment.getCommentContent();
            this.childComment = comment.getChildComment().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.user = new UserSimpleResponse(comment.getUser());
         }

}
