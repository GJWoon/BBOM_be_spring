package com.laonstory.bbom.domain.content.application;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.laonstory.bbom.domain.content.domain.Comment;
import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.dto.CommentDto;
import com.laonstory.bbom.domain.content.dto.CommentResponse;
import com.laonstory.bbom.domain.content.dto.CommentUpdateDto;
import com.laonstory.bbom.domain.content.repository.CommentRepository;
import com.laonstory.bbom.domain.content.repository.CommentRepositorySupport;
import com.laonstory.bbom.domain.content.repository.ContentRepository;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.config.FireBase;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import com.laonstory.bbom.global.error.exception.BusinessException;
import com.laonstory.bbom.global.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ContentRepository contentRepository;
    private final FireBase push;
    public Long postComment(CommentDto dto, User user){
        Content content = contentRepository.findById(dto.getContentId()).orElse(null);
        Comment comment = Comment
                .builder()
                .commentContent(dto.getContent())
                .user(user)
                .content(content)
                .build();
        comment.setCreatedDate();
        if(dto.getParentId() != null && !dto.getParentId().equals(0L)) {
          Comment parentComment = commentRepository.findById(dto.getParentId()).orElse(null);
            assert parentComment != null;
            comment.addParentComment(parentComment);
            try {
                push.sendMessage(parentComment.getUser().getFcmToken(),user.getNickName()+"님이 댓글에 답글을 남겼어요",dto.getContent());
            } catch (IOException | FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
        commentRepository.save(comment);
        assert content != null;
        content.getCommentList().add(comment);
        try {
            push.sendMessage(content.getUser().getFcmToken(),user.getNickName()+"님이 게시글에 댓글을 남겼어요",dto.getContent());
        } catch (IOException | FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return comment.getId();
    }


    public PagingResponse<CommentResponse> findAllPaging(int page,Long contentId,int limit,User user){
        Pageable pageable = new PageRequest(page,limit).of();
        Page<Comment> result = commentRepositorySupport.findAllPaging(pageable,contentId,user);
        if(user == null){
            return new PagingResponse<>(result.map(CommentResponse::new));

        }
        return new PagingResponse<>(result.map(e->new CommentResponse(e,user)));
    }
    public Boolean deleteComment(Long id){
        commentRepository.deleteById(id);
        return true;
    }
    public Boolean updateComment(CommentUpdateDto dto){
        Comment comment = commentRepository.findById(dto.getId()).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
        comment.updateContent(dto);
        return true;
    }
}
