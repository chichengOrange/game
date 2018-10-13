package com.cc.controller.view;

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
    @GetMapping("/{page}")
    public ModelAndView index(@PathVariable String page, HttpServletRequest request, Model model){
        // TODO: 2018/10/13  
        request.getSession().setAttribute("token","e7259f68-0787-4bf0-a4f5-055b1b214979");
        UserModel userModel = new UserModel();
        userModel.setUserId(1L);
        userModel.setUsername("zzzzz");
        request.getSession().setAttribute("user",userModel);
        return new ModelAndView("ar/"+page,"model",model);
    }
}
