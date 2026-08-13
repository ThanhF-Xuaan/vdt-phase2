package com.demo.vdt.modules.booking.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    @PostMapping
    @PreAuthorize("hasAuthority('BOOKING_CREATE')")
    public Map<String, Object> createBooking(){
        return Map.of("message", "Booking created successfully!");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BOOKING_READ')")
    public Map<String, Object> getBookings(){
        return Map.of("message", "Booking list");
    }
}
