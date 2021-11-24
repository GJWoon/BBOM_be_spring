package com.laonstory.bbom.domain.follow.api;


import com.laonstory.bbom.domain.follow.application.FollowService;
import com.laonstory.bbom.domain.follow.dto.FollowUserDto;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("follow")
@RequiredArgsConstructor
public class FollowApi {

    private final FollowService followService;

    @GetMapping()
    public ApiResponse<Boolean> following(@RequestParam(required = true) Long id, @AuthenticationPrincipal User user) {
        return new ApiResponse<>(followService.follow(id, user));
    }

    @GetMapping("/page")
    public ApiPagingResponse<FollowUserDto> findFollowUser(@AuthenticationPrincipal User user,
                                                           @RequestParam(required = false, defaultValue = "1") int page,
                                                           @RequestParam(required = false, defaultValue = "follower") String type
    ) {
        return new ApiPagingResponse<>(followService.findAllPaging(page, user, type));
    }


    @GetMapping("/test")
    public void test(){
        followService.test();
    }
}
