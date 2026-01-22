package com.dashu.dbtool.service;

import com.dashu.dbtool.model.dto.request.UserRegisterReq;

public interface UserService {
    void register(UserRegisterReq req);
}
