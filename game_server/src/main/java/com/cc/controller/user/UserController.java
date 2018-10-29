package com.cc.controller.user;

import com.ancun.netsign.model.NetSignResponse;
import com.cc.common.Utils.AncunUtil;
import com.cc.common.Utils.AssertUtil;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.incCode.IncNoService;
import com.cc.common.incCode.IncTypeEnum;
import com.cc.common.interceptor.MyAdapterInterceptor;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.user.UserModel;
import com.cc.service.user.TokenService;
import com.cc.service.user.UserService;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private IncNoService incNoService;

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
     * 退出登录
     */
    @PostMapping("logout")
    public Result logout(HttpServletRequest request) {

        Long user_id = (Long) request.getAttribute(MyAdapterInterceptor.LOGIN_USER_KEY);
        return saveOrUpdateResult(tokenService.deleteByPrimaryKey(user_id));
    }

    /**
     * 注册
     */
    @Transactional
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


        user.setUserId(incNoService.nextIdByType(IncTypeEnum.TB_USER_ID));

        int r = userService.save(user);

        return saveOrUpdateResult(r);
    }


    /**
     * 实名认证
     */
    @PostMapping("realName")
    public Result realName(@RequestParam("identity") String identity,
                           @RequestParam("real_name") String realName,
                           @RequestParam("mobile") String mobile,
                           String email,
                           HttpServletRequest request) {

        NetSignResponse netSignResponse = AncunUtil.addUser(realName, identity, mobile, email);

        Long userId = (Long) request.getAttribute(MyAdapterInterceptor.LOGIN_USER_KEY);

        if (netSignResponse.getCode() == 100000 || netSignResponse.getCode() == 100024) {
            UserModel user = new UserModel();
            user.setUserId(userId);
            user.setMobile(mobile);
            user.setRealname(realName);
            user.setIdentity(identity);
            user.setRnameStatus(1);
            user.setEmail(email);
            userService.updateByPrimaryKeySelective(user);

            user = userService.queryObject(userId);
            request.getSession().setAttribute("user", user);
            return successResult();
        } else {
            return failResult().message(netSignResponse.getMsg()).code(netSignResponse.getCode());
        }

    }

}
