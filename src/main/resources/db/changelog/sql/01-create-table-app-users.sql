--liquibase formatted sql

--changeset admin:1
CREATE TABLE app_users (
    keycloak_id VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL,
    full_name VARCHAR(255),

    CONSTRAINT pk_app_users
          PRIMARY KEY (keycloak_id),

    CONSTRAINT uk_app_users_username
          UNIQUE (username)
);