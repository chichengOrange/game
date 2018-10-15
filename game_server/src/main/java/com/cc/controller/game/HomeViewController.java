package com.cc.controller.game;

import com.cc.common.annotation.IgnoreToken;
import com.cc.model.user.UserModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeViewController {

    @IgnoreToken
    @GetMapping("ar/{page}")
    public ModelAndView index(@PathVariable String page, HttpServletRequest request, Model model){
        // TODO: 2018/10/13
        UserModel userModel = new UserModel();
        userModel.setUserId(1L);
        userModel.setUsername("zzzzz");
        request.getSession().setAttribute("user",userModel);
        return new ModelAndView("ar/"+page,"model",model);
    }

}
