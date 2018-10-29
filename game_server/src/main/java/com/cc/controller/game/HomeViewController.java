package com.cc.controller.game;

import com.cc.common.annotation.IgnoreToken;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.game.Game;
import com.cc.model.user.UserModel;
import com.cc.service.game.GameService;
import com.cc.service.user.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeViewController extends BaseController {
    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    private final String HOME = "home";
    private final String CONTACT = "contact";
    private final String LOGOUT = "logout";
    private final String REPLAY = "gameReplay";


    @IgnoreToken
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("ar/home");
    }

    @IgnoreToken
    @GetMapping("ar/{page}")
    public ModelAndView index(@PathVariable String page, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String vName = "ar/" + page;

        // TODO: 2018/10/13
        UserModel userModel = new UserModel();
        userModel.setUserId(1L);
        userModel.setUsername("zzzzz");
        //request.getSession().setAttribute("user", userModel);


        Object sessionUser = request.getSession().getAttribute("user");

        switch (page) {
            case HOME: {
                Integer limit = null;
                if (StringUtil.isEmpty(request.getParameter("type"))) {
                    limit = 10;
                }
                List<Game> gameList = gameService.findObjectsByType(request.getParameter("type"), limit);
                model.addAttribute("gameList", gameList);
            }
            break;
            case CONTACT: {
                String show = request.getParameter("show");
                String shows = "showLogin,register,realName";
                if (StringUtil.isEmpty(show) || shows.indexOf(show) == -1) {
                    request.getRequestDispatcher("home").forward(request, response);
                } else if (show.equals("realName") && sessionUser == null) {
                    request.getRequestDispatcher("home").forward(request, response);
                } else if ((show.equals("showLogin") || show.equals("register")) && sessionUser != null) {
                    request.getRequestDispatcher("home").forward(request, response);
                }

            }
            break;
            case REPLAY: {
                if (sessionUser == null) {
                    request.getRequestDispatcher("home").forward(request, response);
                }
            }
            break;
            case LOGOUT: {
                request.getSession().removeAttribute("user");
                request.getRequestDispatcher("home").forward(request, response);
            }
            break;
            default: {
                vName = "ar/" + HOME;
            }
            break;
        }


        List<String> types = gameService.selectAllType();
        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            types.set(i, "<li><a href='home?type=" + type + "'>" + type + "</a></li>");
        }
        //重新设置的ui
        Map supple = new HashMap(16);
        supple.put("types", types);

        supple.put("user", sessionUser);

        model.addAttribute("supple", supple);

        return new ModelAndView(vName, "model", model);
    }


    /**
     * 登录
     */
    @IgnoreToken
    @PostMapping("ar/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {

        //用户登录
        UserModel user = userService.login(username, password);


        request.getSession().setAttribute("user", user);


        request.getSession().setMaxInactiveInterval(24 * 3600);//一天

        return successResult(user);
    }


}
