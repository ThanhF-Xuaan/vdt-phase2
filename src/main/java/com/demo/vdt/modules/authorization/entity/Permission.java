package com.demo.vdt.modules.authorization.entity;

import liquibase.pro.packaged.A;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, unique = true, length = 100)
    String code;

    @Column(nullable = false, length = 150)
    String name;

    @Column(length = 255)
    String description;
}
