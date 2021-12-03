package com.laonstory.bbom.domain.user.api;

import com.laonstory.bbom.domain.user.Application.UserService;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.domain.user.dto.*;
import com.laonstory.bbom.global.dto.response.ApiListResponse;
import com.laonstory.bbom.global.dto.response.ApiPagingResponse;
import com.laonstory.bbom.global.dto.response.ApiResponse;
import com.laonstory.bbom.global.dto.response.PagingResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController()
@RequiredArgsConstructor
@RequestMapping("user")
public class UserApi {

    private final UserService userService;

    @PostMapping()
    public ApiResponse<Boolean> resister(@RequestPart UserResisterDto dto, @RequestPart(required = false) MultipartFile image) throws IOException {
        return new ApiResponse<>(userService.resisterUser(dto, image));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginDto dto) {
        return new ApiResponse<>(userService.login(dto));
    }

    @GetMapping("/duplicate/nickname")
    public ApiResponse<Boolean> duplicateNickName(@RequestParam() String query) {
        return new ApiResponse<>(userService.checkNickName(query));
    }

    @GetMapping("/duplicate/email")
    public ApiResponse<Boolean> duplicateEmail(@RequestParam() String query) {
        return new ApiResponse<>(userService.checkEmail(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserGetMeResponse> findById(@PathVariable Long id, @AuthenticationPrincipal User user) {

        return new ApiResponse(userService.findById(id, user));
    }

    @GetMapping("/me")
    public ApiResponse<UserGetMeResponse> findMe(@AuthenticationPrincipal User user) {
        return new ApiResponse<>(userService.findMe(user));
    }

    @GetMapping("/admin")
    public ApiPagingResponse<UserResponse> findAllPaging(@RequestParam(defaultValue = "1", required = false) int page) {

        return new ApiPagingResponse<>(userService.findAllUser(page));
    }

    @PatchMapping("")
    public ApiResponse<Boolean> updateUser(@RequestPart UserUpdateDto dto, @AuthenticationPrincipal User user,
                                           @RequestPart(required = false) MultipartFile image

    ) throws IOException {
        return new ApiResponse<>(userService.userUpdate(dto, image, user));
    }

    @DeleteMapping("")
    public ApiResponse<Boolean> deleteUser(@AuthenticationPrincipal User user) {
        return new ApiResponse<>(userService.deleteUser(user));
    }

    @GetMapping("/logout")
    public ApiResponse<Boolean> logout(HttpServletRequest req, HttpServletResponse response) {

        return new ApiResponse<>(true, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ApiResponse<Boolean> updatePassword(@RequestBody UpdatePasswordDto dto, @AuthenticationPrincipal User user) {
        return new ApiResponse<>(userService.updatePassword(dto, user));
    }

    @PostMapping("/fcm")
    public ApiResponse<Boolean> updateFcm(@RequestBody Map<String, String> fcmDto, @AuthenticationPrincipal User user) {
        return new ApiResponse<>(userService.updateFcm(fcmDto, user));
    }

    @GetMapping("/alarm")
    public ApiListResponse<AlarmResponse> getAlarm(@AuthenticationPrincipal User user) {
        return new ApiListResponse<>(userService.getAlarm(user));
    }

    @GetMapping("/search")
    public ApiPagingResponse<UserResponseWithFollow> search(@RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                        @AuthenticationPrincipal User user) {
    return new ApiPagingResponse<>(userService.search(user,query,page,limit));
    }


}
