package com.laonstory.bbom.domain.content.api;


import com.laonstory.bbom.domain.content.application.CommentService;
import com.laonstory.bbom.domain.content.dto.CommentDto;
import com.laonstory.bbom.domain.content.dto.CommentResponse;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentApi {

    private final CommentService commentService;

    @PostMapping()
    public ApiResponse<Boolean> postComment(@RequestBody CommentDto dto,
                                            @AuthenticationPrincipal User user
    ) {
        return new ApiResponse<>(commentService.postComment(dto, user));
    }
    @GetMapping()
    public ApiPagingResponse<CommentResponse> findComment(@RequestParam(required = false, defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10", required = false) int limit
                                                        , @RequestParam Long contentId
    ) {
        return new ApiPagingResponse<>(commentService.findAllPaging(page, contentId, limit
        ));
    }
}
