package com.dashu.dbtool.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

}
