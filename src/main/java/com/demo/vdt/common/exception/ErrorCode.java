package com.demo.vdt.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

    USERID_NOT_FOUND(1001, "User doesn't exist!", HttpStatus.NOT_FOUND),

    FORBIDDEN(1002, "Access denied", HttpStatus.FORBIDDEN),
    ;
    ErrorCode(int code, String message, HttpStatus statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatus statusCode;
}
