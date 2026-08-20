package com.demo.vdt.modules.auth.controller;

import com.demo.vdt.modules.auth.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationTestController {
    AuthorizationService authorizationService;

    @GetMapping("/booking")
    public String testBooking(Authentication authentication){
        authorizationService.checkPermission(
                authentication.getName(),
                "BOOKING_READ",
                "Xem dat phong"
        );

        return "Ban co quyen xem dat phong";
    }
}
