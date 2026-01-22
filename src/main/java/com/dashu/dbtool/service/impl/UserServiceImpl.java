package com.dashu.dbtool.service.impl;

import com.dashu.dbtool.common.response.ResponseCode;
import com.dashu.dbtool.exception.BusinessException;
import com.dashu.dbtool.mapper.UserMapper;
import com.dashu.dbtool.model.dto.request.UserRegisterReq;
import com.dashu.dbtool.model.entity.User;
import com.dashu.dbtool.service.UserService;
import com.dashu.dbtool.util.PWDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public void register(UserRegisterReq req) {
         User user = userMapper.findByUsername(req.getUsername());
         if (user != null) {
             throw new BusinessException(ResponseCode.USER_EXISTS);
         }
         user = User.builder()
                 .username(req.getUsername())
                 .password(PWDUtil.hash(req.getPassword()))
                 .build();
         userMapper.insert(user);
    }
}
