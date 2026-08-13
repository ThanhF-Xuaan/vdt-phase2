--liquibase formatted sql

--changeset admin:6
INSERT INTO permissions (code, name, description)
VALUES
    ('USER_CREATE', 'Thêm mới người dùng', 'Quyền thêm mới người dùng'),
    ('USER_READ', 'Xem người dùng', 'Quyền xem thông tin người dùng'),
    ('USER_UPDATE', 'Sửa người dùng', 'Quyền sửa thông tin người dùng'),
    ('USER_DELETE', 'Xóa người dùng', 'Quyền xóa người dùng'),

    ('BOOKING_CREATE', 'Thêm mới đặt phòng', 'Quyền tạo đặt phòng'),
    ('BOOKING_READ', 'Xem đặt phòng', 'Quyền xem đặt phòng'),
    ('BOOKING_UPDATE', 'Sửa đặt phòng', 'Quyền sửa đặt phòng'),
    ('BOOKING_DELETE', 'Xóa đặt phòng', 'Quyền xóa đặt phòng'),

    ('ROOM_CREATE', 'Thêm mới phòng', 'Quyền tạo phòng'),
    ('ROOM_READ', 'Xem phòng', 'Quyền xem phòng'),
    ('ROOM_UPDATE', 'Sửa phòng', 'Quyền sửa phòng'),
    ('ROOM_DELETE', 'Xóa phòng', 'Quyền xóa phòng');


--changeset admin:7
INSERT INTO role_groups (code, name, description)
VALUES
    ('ADMIN', 'Administrator', 'Toàn quyền trên hệ thống'),

    ('RECEPTIONIST', 'Receptionist', 'Quản lý đặt phòng và thông tin liên quan'),

    ('HOUSEKEEPER', 'Housekeeper', 'Quản lý thông tin phòng');


--changeset admin:8
INSERT INTO role_group_permissions (role_group_id, permission_id)
SELECT rg.id, p.id
FROM role_groups rg
         CROSS JOIN permissions p
WHERE rg.code = 'ADMIN';



--changeset admin:9
INSERT INTO role_group_permissions (role_group_id, permission_id)
SELECT rg.id, p.id
FROM role_groups rg
         JOIN permissions p
              ON p.code IN (
                    'BOOKING_CREATE',
                    'BOOKING_READ',
                    'BOOKING_UPDATE',
                    'BOOKING_DELETE',
                    'ROOM_READ'
                  )
WHERE rg.code = 'RECEPTIONIST';


--changeset admin:10
INSERT INTO role_group_permissions (role_group_id, permission_id)
SELECT rg.id, p.id
FROM role_groups rg
         JOIN permissions p
              ON p.code IN (
                    'ROOM_READ',
                    'ROOM_UPDATE'
                  )
WHERE rg.code = 'HOUSEKEEPER';



