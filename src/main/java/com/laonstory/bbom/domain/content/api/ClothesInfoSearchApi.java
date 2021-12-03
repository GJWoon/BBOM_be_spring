package com.laonstory.bbom.domain.content.api;


import com.laonstory.bbom.domain.content.application.ContentService;
import com.laonstory.bbom.domain.content.dto.ClotheInfoSearchResponse;
import com.laonstory.bbom.domain.content.dto.ClothesInfoResponse;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class ClothesInfoSearchApi {


        private final ContentService contentService;


        @GetMapping("/search")
        public ApiPagingResponse<ClotheInfoSearchResponse> search(@RequestParam(defaultValue = "1",required = false) int page,
                                                                  @RequestParam(defaultValue = "10",required = false) int limit,
                                                                  @RequestParam(required = false) String query
        ){
                return new ApiPagingResponse(contentService.searchInfo(page, limit, query));
        }


}
