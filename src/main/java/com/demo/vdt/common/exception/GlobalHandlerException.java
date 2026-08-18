package com.demo.vdt.common.exception;

import com.demo.vdt.common.annotation.ApiFunction;
import com.demo.vdt.common.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalHandlerException {
    MessageSource messageSource;
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    private String getLocalizedMessage(String messageKey, String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            return messageSource.getMessage(messageKey, null, locale);
        } catch (NoSuchMessageException e){
            return defaultMessage;
        }
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException){
        log.error("Exception: ", runtimeException);

        String localizedMessage = getLocalizedMessage(
                ErrorCode.UNCATEGORIZED_EXCEPTION.name(),
                ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()
        );

        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                .body(ApiResponse.builder()
                        .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                        .message(localizedMessage)
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException){
        ErrorCode errorCode = appException.getErrorCode();

        String localizedMessage = getLocalizedMessage(errorCode.name(), errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(localizedMessage)
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception,
                                                              HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "unknown";

        String functionName = resolveFunctionName(request);
        String message = String.format("user %s khong co quyen truy cap chuc nang %s", username, functionName);

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(message)
                        .build());
    }

    // Hàm phụ trợ map URL ra tên chức năng
    private String resolveFunctionName(HttpServletRequest request) {
        try{
            HandlerExecutionChain handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);

            if(handlerExecutionChain != null && handlerExecutionChain.getHandler() instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();

                ApiFunction apiFunction = handlerMethod.getMethodAnnotation(ApiFunction.class);

                if(apiFunction != null){
                    String functionKey = apiFunction.value();
                    return getLocalizedMessage(functionKey, functionKey);
                }

                return handlerMethod.getMethod().getName();
            }

        }catch (Exception e){
            log.warn("Khong the map request", e);
        }
        return request.getRequestURI();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        String finalMessage = enumKey;

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        try{
            errorCode = ErrorCode.valueOf(enumKey);

            finalMessage = getLocalizedMessage(enumKey, errorCode.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .code(400)
                            .message(finalMessage)
                            .build()
            );
        }

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(finalMessage)
                        .build());
    }
}