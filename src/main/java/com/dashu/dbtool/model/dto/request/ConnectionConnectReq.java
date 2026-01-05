package com.dashu.dbtool.model.dto.request;

import lombok.Data;

@Data
public class ConnectionConnectReq {
    private String jdbcUrl;
    private String username;
    private String password;
}
