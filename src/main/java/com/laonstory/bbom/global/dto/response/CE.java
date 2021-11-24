package com.laonstory.bbom.global.dto.response;

import com.laonstory.bbom.global.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CE {

    private  int status;
    private  String code;
    private  String message;


    public CE(final int status, final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
