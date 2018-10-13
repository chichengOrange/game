//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user.impl;

import com.cc.common.Utils.AssertUtil;
import com.cc.common.Utils.DateUtils;
import com.cc.common.Utils.MD5Encode;
import com.cc.common.Utils.RRException;
import com.cc.mapper.user.UserMapper;
import com.cc.model.user.UserModel;
import com.cc.service.user.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl() {
    }

    public UserModel queryObject(Long userId) {
        return (UserModel)this.userMapper.selectByPrimaryKey(userId);
    }

    public void save(UserModel user) {
        user.setUserId(DateUtils.currentTimeMillis());
        user.setPassword(MD5Encode.encryption(user.getPassword()));
        user.setCreateTime(new Date());
        this.userMapper.insertSelective(user);
    }

    public void update(UserModel user) {
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    public UserModel queryByMobile(String mobile) {
        return this.userMapper.queryByMobile(mobile);
    }

    public long login(String username, String password) {
        UserModel user = this.userMapper.queryByUsername(username);
        AssertUtil.isNull(user, "账号或密码错误");
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RRException("此账号已被禁用 请联系管理员", 300);
        } else if (!user.getPassword().equals(password)) {
            throw new RRException("账号或密码错误", 300);
        } else {
            return user.getUserId();
        }
    }
}
