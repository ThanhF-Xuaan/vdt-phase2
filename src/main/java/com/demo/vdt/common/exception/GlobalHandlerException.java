package com.demo.vdt.common.exception;

import com.demo.vdt.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException){
        log.error("Exception: ", runtimeException);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity
                .status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException){
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(appException.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception,
                                                              HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "unknown";

        String functionName = resolveFunctionName(request.getMethod(), request.getRequestURI());
        String message = String.format("user %s khong co quyen truy cap chuc nang %s", username, functionName);

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    // Hàm phụ trợ map URL ra tên chức năng
    private String resolveFunctionName(String method, String uri) {
        if ("POST".equals(method) && "/api/bookings".equals(uri)) {
            return "Thêm mới đặt phòng";
        }
        if ("GET".equals(method) && "/api/bookings".equals(uri)) {
            return "Xem danh sách đặt phòng";
        }
        if ("PUT".equals(method) && uri.startsWith("/api/bookings/")) {
            return "Sửa đặt phòng";
        }
        if ("DELETE".equals(method) && uri.startsWith("/api/bookings/")) {
            return "Xóa đặt phòng";
        }
        return uri;
    }
}
