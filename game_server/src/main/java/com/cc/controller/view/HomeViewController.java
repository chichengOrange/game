package com.cc.controller.view;

import com.cc.common.annotation.IgnoreToken;
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
        return new ModelAndView("ar/"+page,"model",model);
    }
}
