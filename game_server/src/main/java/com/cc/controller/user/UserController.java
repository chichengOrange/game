package com.cc.controller.user;

import com.cc.common.Utils.AssertUtil;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.user.UserModel;
import com.cc.service.user.TokenService;
import com.cc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping({"/user"})
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    public UserController() {
    }

    @IgnoreToken
    @PostMapping({"login"})
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        long userId = this.userService.login(username, password);
        Map<String, Object> map = this.tokenService.createToken(userId);
        return this.successResult(map);
    }

    @IgnoreToken
    @PostMapping({"register"})
    public Result register(UserModel user) {
        AssertUtil.isBlank(user.getUsername(), "用户名不能为空");
        AssertUtil.isBlank(user.getPassword(), "密码不能为空");
        this.userService.save(user);
        return this.successResult();
    }
}
