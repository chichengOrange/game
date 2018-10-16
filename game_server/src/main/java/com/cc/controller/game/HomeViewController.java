package com.cc.controller.game;

import com.cc.common.annotation.IgnoreToken;
import com.cc.common.result.PageResult;
import com.cc.model.game.Game;
import com.cc.model.user.UserModel;
import com.cc.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeViewController {
    @Autowired
    private GameService gameService;

    @IgnoreToken
    @GetMapping("ar/{page}")
    public ModelAndView index(@PathVariable String page, HttpServletRequest request, Model model){
        // TODO: 2018/10/13
        UserModel userModel = new UserModel();
        userModel.setUserId(1L);
        userModel.setUsername("zzzzz");
        request.getSession().setAttribute("user",userModel);


       PageResult<Game> pageResult = gameService.findListByPage(1,10,null);

        List<Game> gameList = (List<Game>) pageResult.getData();


        List<String> types = gameService.selectAllType();

        String[] tyArrats  = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            tyArrats[i]=" <li><a href='category?type="+type+"'>"+type+"</a></li>";
        }


        //重新设置的ui
        Map supple = new HashMap(16);
        supple.put("types",tyArrats);
        supple.put("selected",page);

        model.addAttribute("supple",supple);

        model.addAttribute("gameList",gameList);
        return new ModelAndView("ar/"+page,"model",model);
    }

}
