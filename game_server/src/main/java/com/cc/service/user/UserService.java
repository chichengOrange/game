//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user;

import com.cc.model.user.UserModel;

public interface UserService {
    UserModel queryObject(Long userId);

    void save(UserModel user);

    void update(UserModel user);

    UserModel queryByMobile(String mobile);

    long login(String username, String password);
}
