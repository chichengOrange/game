//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user;

import com.cc.mapper.user.UserMapper;
import com.cc.model.user.UserModel;
import com.cc.service.BaseService;

public interface UserService extends BaseService<UserModel, UserMapper> {


    UserModel queryObject(Long userId);

    int save(UserModel user);

    int update(UserModel user);

    UserModel queryByMobile(String mobile);

    UserModel login(String username, String password);
}
