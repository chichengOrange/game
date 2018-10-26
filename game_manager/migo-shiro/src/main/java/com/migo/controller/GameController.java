//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.controller;

import com.migo.annotation.SysLog;
import com.migo.baseController.BaseController;
import com.migo.entity.Game;
import com.migo.enums.ResultCode;
import com.migo.incCode.IncNoService;
import com.migo.incCode.IncTypeEnum;
import com.migo.result.Result;
import com.migo.service.GameService;
import com.migo.utils.ConfigUtil;
import com.migo.utils.FileUtil;
import com.migo.utils.R;
import com.migo.utils.ShiroUtils;
import com.migo.validator.ValidatorUtils;
import com.migo.validator.group.AddGroup;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/sys/game"})
public class GameController extends BaseController {
    @Autowired
    private GameService gameService;

    @Autowired
    private IncNoService incNoService;

    public GameController() {
    }

    @GetMapping({"/list"})
    @RequiresPermissions({"sys:game:list"})
    public Result list(@RequestParam(value = "page",required = false,defaultValue = "1") int page, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize, String search, String sidx, String order) {
        Result result = this.gameService.findListByPage(page, pageSize, search, sidx, order);
        return result;
    }


    /*@Transactional(rollbackFor = {Exception.class})*/
    @SysLog("保存游戏")
    @PostMapping({"/save"})
    public Result save(Game game, @RequestParam("pictureAtt") MultipartFile pictureAtt, MultipartFile videoAtt, MultipartFile packageAtt, HttpServletRequest request) throws IOException {
        ValidatorUtils.validateEntity(game, new Class[]{AddGroup.class});
        if (pictureAtt.isEmpty()) {
            return new Result(ResultCode.FAIL,null);
        } else {
            String basePath = ConfigUtil.getSysParam("base_path", "/usr/local/tomcat/webapps");
            String imgPath = this.saveFile(pictureAtt, basePath, "/img/");
            if (imgPath == null) {
                return new Result(ResultCode.FAIL, null);
            } else {
                game.setPicture(ConfigUtil.getSysParam("base_url", "http://47.99.61.151:9002") + "/img/" + pictureAtt.getOriginalFilename().replace(" ", ""));
                game.setVideo(this.saveFile(videoAtt, basePath, "/video/"));
                game.setAppPackage(this.saveFile(packageAtt, basePath, "/package/"));
                game.setCreator(ShiroUtils.getUserId());

                game.setId(incNoService.nextIdByType(IncTypeEnum.GAME_ID));
                game.setCreateTime(new Date());
                game.setUpdateTime(game.getCreateTime());
                game.setDescription(game.getDescription().replace("\r\n", "<br/>"));
                int r = this.gameService.insertSelective(game);
                return Result.saveOrUpdate(r);
            }
        }
    }

    @SysLog("更新游戏")
    @PostMapping({"/update"})
    public Result update(Game game, MultipartFile pictureAtt, MultipartFile videoAtt, MultipartFile packageAtt) throws IOException {
        ValidatorUtils.validateEntity(game, new Class[]{AddGroup.class});
        String basePath = ConfigUtil.getSysParam("base_path", "/usr/local/tomcat");
        String imgPath = this.saveFile(pictureAtt, basePath, "/img/");
        if (imgPath != null) {
            game.setPicture(ConfigUtil.getSysParam("base_url", "http://47.99.61.151:9002") + "/img/" + pictureAtt.getOriginalFilename().replace(" ", ""));
        } else {
            game.setPicture(null);
        }

        game.setVideo(this.saveFile(videoAtt, basePath, "/video/"));
        game.setAppPackage(this.saveFile(packageAtt, basePath, "/package/"));
        game.setUpdateTime(new Date());
        game.setDescription(game.getDescription().replace("\r\n", "<br/>"));
        int r = this.gameService.updateByPrimaryKeySelective(game);
        return Result.saveOrUpdate(r);
    }

    private String saveFile(MultipartFile file, String basePath, String midPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            String saveFileName = file.getOriginalFilename().replace(" ", "");
            String filePath = basePath + midPath + saveFileName;
            FileUtil.uploadFile(file, filePath);
            return filePath;
        } else {
            return null;
        }
    }

    @GetMapping({"/info/{id}"})
    public Result list(@PathVariable Long id) {
        Game game = this.gameService.selectByPrimaryKey(id);
        return Result.success(game);
    }


    /**
     * 删除游戏
     */
    @SysLog("删除游戏")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:game:delete")
    public R delete(@RequestBody Long[] gameIds){

        int r =  gameService.deleteBatch(gameIds);

        return r> 0?R.ok():R.error(300,"批量删除失败");
    }
}
