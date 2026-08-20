package com.demo.vdt.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1015, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    USER_NOT_EXISTED(1001, "User doesn't exist!", HttpStatus.NOT_FOUND),

    FORBIDDEN(1002, "Access denied", HttpStatus.FORBIDDEN),

    INVALID_DATETIME_FORMAT(1003, "Ngày tháng nhập vào không hợp lệ hoặc không tồn tại trên lịch!", HttpStatus.BAD_REQUEST),

    DATETIME_NOT_VN_FORMAT(1004, "Ngày tháng bắt buộc phải nhập theo chuẩn Việt Nam (dd/MM/yyyy). Ví dụ: 01/01/1990", HttpStatus.BAD_REQUEST),

    USER_EXISTED(1005, "Username already exists!", HttpStatus.BAD_REQUEST),

    USERNAME_NOT_BLANK(1008, "Tên đăng nhập không được để trống", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID_SIZE(1009, "Tên đăng nhập phải có ít nhất 4 ký tự", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_BLANK(1010, "Mật khẩu không được để trống", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_SIZE(1011, "Mật khẩu phải có ít nhất 6 ký tự", HttpStatus.BAD_REQUEST),
    FIRST_NAME_NOT_BLANK(1012, "Tên không được để trống", HttpStatus.BAD_REQUEST),
    LAST_NAME_NOT_BLANK(1013, "Họ không được để trống", HttpStatus.BAD_REQUEST),
    DOB_NOT_BLANK(1014, "Ngày sinh không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_GROUP_EXISTED(1015, "Role group code already exists", HttpStatus.BAD_REQUEST),
    ROLE_GROUP_NOT_EXISTED(1016, "Role group doesn't exist", HttpStatus.NOT_FOUND),
    USER_ROLE_GROUP_NOT_EMPTY(1017, "You must assign at least one role group to the user", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1018, "Permission code already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1019, "Permission doesn't exist", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_CORE_PERMISSION(1020, "Cannot delete core system permissions", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1021, "Invalid date or birth", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_BLANK(1022, "Refresh token is required", HttpStatus.BAD_REQUEST),
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
