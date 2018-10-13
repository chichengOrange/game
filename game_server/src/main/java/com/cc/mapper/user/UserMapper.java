package com.cc.mapper.user;

import com.cc.mapper.BaseMapper;
import com.cc.model.user.UserModel;
import org.apache.ibatis.annotations.Param;

public interface  UserMapper extends BaseMapper<UserModel>{
    UserModel queryByMobile(@Param("mobile") String mobile);

    UserModel queryByUsername(@Param("username") String username);
}
