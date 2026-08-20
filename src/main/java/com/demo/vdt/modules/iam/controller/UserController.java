package com.demo.vdt.modules.iam.controller;

import com.demo.vdt.common.dto.ApiResponse;
import com.demo.vdt.modules.iam.dto.request.UserCreationRequest;
import com.demo.vdt.modules.iam.dto.request.UserUpdateRequest;
import com.demo.vdt.modules.iam.dto.response.UserInfoResponse;
import com.demo.vdt.modules.iam.service.AppUserService;
import com.demo.vdt.modules.iam.service.UserExportService;
import com.demo.vdt.modules.iam.service.UserSyncService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    AppUserService appUserService;
    UserSyncService userSyncService;
    UserExportService userExportService;

    @GetMapping("/me")
    public ApiResponse<Object> me(Authentication authentication){
        return ApiResponse.builder()
                .result(authentication)
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserInfoResponse> register(@RequestBody @Valid UserCreationRequest userCreationRequest){

        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.registerUser(userCreationRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserInfoResponse>> getUsers(){
        return ApiResponse.<List<UserInfoResponse>>builder()
                .result(appUserService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserInfoResponse> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserInfoResponse> updateUser(@PathVariable("userId") Long userId,
                                                    @RequestBody @Valid UserUpdateRequest userUpdateRequest){
        return ApiResponse.<UserInfoResponse>builder()
                .result(appUserService.updateUser(userId, userUpdateRequest))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") Long userId){
        appUserService.deleteUser(userId);

        return ApiResponse.<String>builder()
                .result("Delete user successfully!")
                .build();
    }

    @PostMapping("/sync-keycloak")
//    @PreAuthorize("hasAuthority('ROLE_GROUP_ADMIN')")
    public ResponseEntity<String> triggerSyncManual() {
        userSyncService.syncUsersFromKeycloak();
        return ResponseEntity.ok("Đã đồng bộ thành công dữ liệu từ Keycloak!");
    }

    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> exportUsers(){
        StreamingResponseBody responseBody = outputStream -> {
            userExportService.exportUsersToExcel(outputStream);
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "Danh_sach_nguoi_dung.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }
}
