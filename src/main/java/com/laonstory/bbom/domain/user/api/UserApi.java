package com.laonstory.bbom.domain.user.api;

import com.laonstory.bbom.domain.user.Application.UserService;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.*;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequiredArgsConstructor
@RequestMapping("user")
public class UserApi {

    private final UserService userService;

    @PostMapping()
    public ApiResponse<Boolean> resister(@RequestPart UserResisterDto dto, @RequestPart(required = false) MultipartFile image) throws IOException {
        return new ApiResponse<>(userService.resisterUser(dto,image));
    }
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginDto dto){
        return new ApiResponse<>(userService.login(dto));
    }

    @GetMapping("/duplicate/nickname")
    public ApiResponse<Boolean> duplicateNickName(@RequestParam()String query){
            return new ApiResponse<>(userService.checkNickName(query));
    }

    @GetMapping("/duplicate/email")
    public ApiResponse<Boolean> duplicateEmail(@RequestParam()String query){
        return new ApiResponse<>(userService.checkEmail(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserGetMeResponse> findById(@PathVariable Long id,@AuthenticationPrincipal User user ){

        return new ApiResponse(userService.findById(id,user));
    }
    @GetMapping("/me")
    public ApiResponse<UserGetMeResponse> findMe(@AuthenticationPrincipal User user){
        return new ApiResponse<>(userService.findMe(user));
    }

    @GetMapping("/admin")
    public ApiPagingResponse<UserResponse> findAllPaging(@RequestParam(defaultValue = "1",required = false) int page){

        return new ApiPagingResponse<>(userService.findAllUser(page));
    }

}
