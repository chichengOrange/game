//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user.impl;

import com.cc.common.Utils.AssertUtil;
import com.cc.common.Utils.RRException;
import com.cc.mapper.user.UserMapper;
import com.cc.model.user.UserModel;
import com.cc.service.BaseServiceImpl;
import com.cc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserModel,UserMapper> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserModel queryObject(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int save(UserModel user) {
        user.setCreateTime(new Date());
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(UserModel user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public UserModel queryByMobile(String mobile) {
        return userMapper.queryByMobile(mobile);
    }

    @Override
    public UserModel login(String username, String password) {
        UserModel user = userMapper.queryByUsername(username);
        AssertUtil.isNull(user, "账号或密码错误");


        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RRException("此账号已被禁用 请联系管理员", 300);
        }

        //密码错误
        if (!user.getPassword().equals(password)) {
            throw new RRException("账号或密码错误", 300);
        }


        return user;
    }

}