--liquibase formatted sql

--changeset admin:1
CREATE TABLE app_users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    dob DATE,

    CONSTRAINT pk_app_users
          PRIMARY KEY (user_id),

    CONSTRAINT uk_app_users_username
          UNIQUE (username)
);