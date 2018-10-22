//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user;

import com.cc.model.user.UserModel;

public interface UserService {


    UserModel queryObject(Long userId);

    int save(UserModel user);

    int update(UserModel user);

    UserModel queryByMobile(String mobile);

    UserModel login(String username, String password);
}
