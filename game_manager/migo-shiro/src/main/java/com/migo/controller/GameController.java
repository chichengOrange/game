package com.migo.controller;

import com.migo.annotation.SysLog;
import com.migo.baseController.BaseController;
import com.migo.entity.Game;
import com.migo.enums.ResultCode;
import com.migo.result.Result;
import com.migo.service.GameService;
import com.migo.utils.ConfigUtil;
import com.migo.utils.FileUtil;
import com.migo.utils.ShiroUtils;
import com.migo.validator.ValidatorUtils;
import com.migo.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author jerry.chen
 * @date 2018/9/15 16:06
 **/
@RestController
@RequestMapping("/sys/game")
public class GameController extends BaseController {

    @Autowired
    private GameService gameService;

    /**
     * 所有用游戏列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:game:list")
    public Result list(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                       String search,
                       String sidx, String order) {

        Result result = gameService.findListByPage(page, pageSize, search, sidx, order);

        return result;

    }


    /**
     * save游戏
     */
    @SysLog("保存游戏")
    @PostMapping("/save")
    // @RequiresPermissions("sys:tbUser:list")
    public Result save(Game game, @RequestParam("pictureAtt") MultipartFile pictureAtt, MultipartFile videoAtt, MultipartFile packageAtt, HttpServletRequest request) throws IOException {

        ValidatorUtils.validateEntity(game, AddGroup.class);

        if (pictureAtt.isEmpty()){
            return new Result(ResultCode.FAIL, null);
        }
        String basePath = ConfigUtil.getSysParam("base_path", "/usr/local/tomcat/webapps");

        String imgPath = saveFile(pictureAtt, basePath, "/img/");

        if (imgPath == null)
            return new Result(ResultCode.FAIL, null);

        game.setPicture(ConfigUtil.getSysParam("base_url", "http://47.99.61.151:9002") + "/img/" + pictureAtt.getOriginalFilename().replace(" ", ""));
        game.setVideo(saveFile(videoAtt, basePath, "/video/"));
        game.setAppPackage(saveFile(packageAtt, basePath, "/package/"));

        game.setCreator(ShiroUtils.getUserId());
        game.setId(System.currentTimeMillis());
        game.setCreateTime(new Date());
        game.setUpdateTime(game.getCreateTime());

        game.setDescription(game.getDescription().replace("\r\n", "<br/>"));
        int r = gameService.insertSelective(game);
        return Result.saveOrUpdate(r);
    }


    /**
     * update游戏
     */
    @SysLog("更新游戏")
    @PostMapping("/update")
    // @RequiresPermissions("sys:tbUser:list")
    public Result update(Game game, MultipartFile pictureAtt, MultipartFile videoAtt, MultipartFile packageAtt, HttpServletRequest request) throws IOException {

        ValidatorUtils.validateEntity(game, AddGroup.class);

        String basePath = ConfigUtil.getSysParam("base_path", "/usr/local/tomcat");

        String imgPath = saveFile(pictureAtt, basePath, "/img/");

        if (imgPath != null) {
            game.setPicture(ConfigUtil.getSysParam("base_url", "http://47.99.61.151:9002") + "/img/" + pictureAtt.getOriginalFilename().replace(" ", ""));
        }else {
            game.setPicture(null);
        }

        game.setVideo(saveFile(videoAtt, basePath, "/video/"));
        game.setAppPackage(saveFile(packageAtt, basePath, "/package/"));

        game.setUpdateTime(new Date());
        game.setDescription(game.getDescription().replace("\r\n", "<br/>"));
        int r = gameService.updateByPrimaryKeySelective(game);
        return Result.saveOrUpdate(r);
    }

    /**
     * @param file     MultipartFile
     * @param basePath 基础路径
     * @param midPath  中间路径
     * @return
     * @throws IOException
     */
    private String saveFile(MultipartFile file, String basePath, String midPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            String saveFileName = file.getOriginalFilename().replace(" ", "");
            String filePath = basePath + midPath + saveFileName;
            FileUtil.uploadFile(file, filePath);
            return filePath;
        }
        return null;
    }


    @GetMapping("/info/{id}")
    public Result list(@PathVariable Long id) {
        Game game = gameService.selectByPrimaryKey(id);
        game.setCreateTime(null);
        game.setUpdateTime(null);
        return Result.success(game);
    }

}
