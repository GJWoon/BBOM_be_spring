package com.laonstory.bbom.domain.content.api;


import com.laonstory.bbom.domain.content.application.ContentService;
import com.laonstory.bbom.domain.content.dto.*;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("content")
@RequiredArgsConstructor
public class ContentApi {

    private final ContentService contentService;

    @PostMapping()
    public ApiResponse<Boolean> postContent(
            @RequestPart() List<MultipartFile> infoImages,
            @RequestPart() List<MultipartFile> contentImages,
            @RequestPart @Parameter(schema = @Schema(name = "json", type = "string", format = "binary")) ContentDto dto,
            @AuthenticationPrincipal User user
    ) {
        return new ApiResponse<>(contentService.postContent(infoImages, contentImages, dto, user));
    }

    @GetMapping()
    public ApiPagingResponse<ContentResponse> findAll(@RequestParam(defaultValue = "1", required = false, name = "page") int page,
                                                      @RequestParam(required = false) String query,
                                                      @AuthenticationPrincipal User user,
                                                      @RequestParam(required = false) String type,
                                                      @RequestParam(required = false) String weight,
                                                      @RequestParam(required = false) String height,
                                                      @RequestParam(required = false) String size

    ) {
        return new ApiPagingResponse<>(contentService.findAllPaging(page, query, user,type,weight,height,size));
    }



    @GetMapping("/search") ApiPagingResponse<ContentMyPageResponse> search(

            @RequestParam(defaultValue = "1", required = false, name = "page") int page,
            @RequestParam(required = false) String query
    ){
        return new ApiPagingResponse<>(contentService.findContentMyPageResponse(page, query));
    }




    @GetMapping("/{id}")
    public ApiResponse<ContentDetailResponse> findById(@PathVariable Long id,
                                                       @AuthenticationPrincipal User user
    ) {
        return new ApiResponse<>(contentService.findById(id, user));
    }
    @GetMapping("/like")
    public ApiResponse<Boolean> like(@RequestParam Long id, @AuthenticationPrincipal User user) {
        return new ApiResponse<>(contentService.like(id, user));
    }
    @GetMapping("/suggestion")
    public ApiResponse<ContentResponseListWithUserResponseList> findSuggestion(@AuthenticationPrincipal User user){
        return new ApiResponse<>(contentService.suggestion(user));
    }
    @GetMapping("/{id}/page")
    public ApiPagingResponse<ContentMyPageResponse> findContentMyPageResponse(@PathVariable Long id,
                                                                              @RequestParam(required = false,defaultValue = "1") int page

    ){
        return new ApiPagingResponse<>(contentService.findContentMyPageResponse(id,page));
    }
    @GetMapping("/mine")
    public ApiPagingResponse<ContentMyPageResponse> findContentMyPageResponse(@AuthenticationPrincipal User user,
                                                                              @RequestParam(required = false,defaultValue = "1") int page
    ){
        return new ApiPagingResponse<>(contentService.findContentMyPageResponse(user.getId(),page));
    }}
