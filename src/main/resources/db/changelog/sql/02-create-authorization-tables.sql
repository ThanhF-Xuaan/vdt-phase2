--liquibase formatted sql

--changeset admin:2
CREATE TABLE permissions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(150) NOT NULL,
        description VARCHAR(255),

    CONSTRAINT pk_permissions
        PRIMARY KEY (id),

    CONSTRAINT uk_permissions_code
        UNIQUE (code)
);


--changeset admin:3
CREATE TABLE role_groups (
     id BIGINT NOT NULL AUTO_INCREMENT,
     code VARCHAR(50) NOT NULL,
     name VARCHAR(100) NOT NULL,
     description VARCHAR(255),

     CONSTRAINT pk_role_groups
        PRIMARY KEY (id),

     CONSTRAINT uk_role_groups_code
        UNIQUE (code)
);


--changeset admin:4
CREATE TABLE user_role_groups (
     user_id BIGINT NOT NULL,
     role_group_id BIGINT NOT NULL,

     CONSTRAINT pk_user_role_groups
        PRIMARY KEY (user_id, role_group_id),

     CONSTRAINT fk_user_role_groups_user
        FOREIGN KEY (user_id)
        REFERENCES app_users (user_id),

     CONSTRAINT fk_user_role_groups_group
        FOREIGN KEY (role_group_id)
        REFERENCES role_groups (id)
);


--changeset admin:5
CREATE TABLE role_group_permissions (
    role_group_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,

    CONSTRAINT pk_role_group_permissions
        PRIMARY KEY (role_group_id, permission_id),

    CONSTRAINT fk_role_group_permissions_group
        FOREIGN KEY (role_group_id)
        REFERENCES role_groups (id),

    CONSTRAINT fk_role_group_permissions_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions (id)
);