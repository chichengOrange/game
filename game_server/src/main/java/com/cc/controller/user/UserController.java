package com.cc.controller.user;

import com.cc.common.Utils.AssertUtil;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.interceptor.MyAdapterInterceptor;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.user.UserModel;
import com.cc.service.user.TokenService;
import com.cc.service.user.UserService;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping({"/user"})
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     */
    @IgnoreToken
    @PostMapping("login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        //用户登录
        UserModel user = userService.login(username, password);


        //生成token
        Map<String, Object> map = tokenService.createToken(user.getUserId());

        return successResult(map);
    }

    /**
     * 登录
     */
    @PostMapping("logout")
    public Result login(HttpServletRequest request) {

        //生成token
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(-1);

        Long user_id = (Long) request.getAttribute(MyAdapterInterceptor.LOGIN_USER_KEY);
        return saveOrUpdateResult(tokenService.deleteByPrimaryKey(user_id));
    }

    /**
     * 注册
     */
    @IgnoreToken
    @PostMapping("register")
    public Result register(UserModel user, HttpServletRequest request) {
        AssertUtil.isBlank(user.getUsername(), "用户名不能为空");
        AssertUtil.isBlank(user.getPassword(), "密码不能为空");
        if (StringUtil.isNotEmpty(user.getMobile())) {
            AssertUtil.isPhone(user.getMobile());
        } else {
            user.setMobile(null);
        }

        if (StringUtil.isNotEmpty(user.getEmail())) {
            AssertUtil.isEmail(user.getEmail());
        } else {
            user.setEmail(null);
        }

        int r = userService.save(user);
        if (r > 0) {
            Map<String, Object> map = tokenService.createToken(user.getUserId());
            return successResult(map);
        }
        return failResult();
    }

}
