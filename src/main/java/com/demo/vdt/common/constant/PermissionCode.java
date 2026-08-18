package com.demo.vdt.common.constant;

import java.util.Set;

public class PermissionCode {
    private PermissionCode(){}

    // --- USER (Quản lý người dùng) ---
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_READ = "USER_READ";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DELETE = "USER_DELETE";

    // --- BOOKING (Quản lý đặt phòng) ---
    public static final String BOOKING_CREATE = "BOOKING_CREATE";
    public static final String BOOKING_READ = "BOOKING_READ";
    public static final String BOOKING_UPDATE = "BOOKING_UPDATE";
    public static final String BOOKING_DELETE = "BOOKING_DELETE";

    // --- ROOM (Quản lý thông tin phòng) ---
    public static final String ROOM_CREATE = "ROOM_CREATE";
    public static final String ROOM_READ = "ROOM_READ";
    public static final String ROOM_UPDATE = "ROOM_UPDATE";
    public static final String ROOM_DELETE = "ROOM_DELETE";

    public static final Set<String> CORE_PERMISSIONS = Set.of(
            USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE,
            BOOKING_CREATE, BOOKING_READ, BOOKING_UPDATE, BOOKING_DELETE,
            ROOM_CREATE, ROOM_READ, ROOM_UPDATE, ROOM_DELETE
    );
}
