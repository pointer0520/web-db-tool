package com.dashu.dbtool.mapper;

import com.dashu.dbtool.model.entity.User;
import jakarta.validation.constraints.NotEmpty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    void insert(User user);
}
