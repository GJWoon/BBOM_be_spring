package com.laonstory.bbom.global.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponse<T> {

    private T data;
    private HttpStatus status;

    public ApiResponse(T data){

        this.data = data;
        this.status = HttpStatus.OK;

    }

}
