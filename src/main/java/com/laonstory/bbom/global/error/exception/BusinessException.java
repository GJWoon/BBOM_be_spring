package com.laonstory.bbom.global.error.exception;

import com.laonstory.bbom.global.dto.response.CE;
import com.laonstory.bbom.global.error.model.ErrorCode;

public class BusinessException extends RuntimeException{



    private ErrorCode errorCode;

    public BusinessException(){

        super(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public BusinessException(String message,ErrorCode errorCode){


        super(message);
        this.errorCode = errorCode;

    }


    public BusinessException(ErrorCode errorCode){

        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode(){

        return errorCode;

    }

}
