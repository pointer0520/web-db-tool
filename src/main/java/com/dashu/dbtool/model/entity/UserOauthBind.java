package com.dashu.dbtool.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserOauthBind {
    private UUID id;
    private UUID userId;
    private String provider;
    private String providerUid;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private LocalDateTime linkedAt;
}
