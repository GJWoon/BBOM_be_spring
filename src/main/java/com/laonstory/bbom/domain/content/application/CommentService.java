package com.laonstory.bbom.domain.content.application;


import com.laonstory.bbom.domain.content.domain.Comment;
import com.laonstory.bbom.domain.content.domain.Content;
import com.laonstory.bbom.domain.content.dto.CommentDto;
import com.laonstory.bbom.domain.content.dto.CommentResponse;
import com.laonstory.bbom.domain.content.repository.CommentRepository;
import com.laonstory.bbom.domain.content.repository.CommentRepositorySupport;
import com.laonstory.bbom.domain.content.repository.ContentRepository;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.dto.request.PageRequest;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ContentRepository contentRepository;

    public Boolean postComment(CommentDto dto, User user){
        Content content = contentRepository.findById(dto.getContentId()).orElse(null);
        Comment comment = Comment
                .builder()
                .commentContent(dto.getContent())
                .user(user)
                .content(content)
                .build();
        if(dto.getParentId() != null && !dto.getParentId().equals(0L)) {
          Comment parentComment = commentRepository.findById(dto.getParentId()).orElse(null);
            assert parentComment != null;
            comment.addParentComment(parentComment);
        }
        commentRepository.save(comment);
        assert content != null;
        content.getCommentList().add(comment);
        return true;
    }


    public PagingResponse<CommentResponse> findAllPaging(int page,Long contentId,int limit){
        Pageable pageable = new PageRequest(page,10).of();
        return new PagingResponse<>(commentRepositorySupport.findAllPaging(pageable,contentId));

    }

}
