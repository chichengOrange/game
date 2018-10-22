package com.cc.controller.game;

import com.cc.common.Utils.AncunUtil;
import com.cc.common.Utils.FileUtils;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.enums.ResultCode;
import com.cc.common.result.PageResult;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.game.GameReplay;
import com.cc.service.game.GameReplayService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"/gameReplay"})
public class GameReplayController extends BaseController {


    @Autowired
    private GameReplayService gameReplayService;


    /**
     * replay列表
     * @param page 第几页
     * @param pageSize 每页条数
     * @param userId 用户id
     * @param gameId 游戏id
     * @param sidx 排序字段
     * @param order 升序还是降序
     * @param search 搜索条件
     * @return
     */
    @IgnoreToken
    @GetMapping({"/list"})
    public PageResult replayList(@RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize,
                                 Long userId, Long gameId, String sidx, String order,String search) {

        PageHelper.startPage(page,pageSize);
        if (StringUtils.isNotEmpty(sidx) && StringUtils.isNotEmpty(order)){
            PageHelper.orderBy(sidx+" "+order);
        }

        List<GameReplay> gameReplays = gameReplayService.findReplays(gameId,userId,search);
        return new PageResult(gameReplays);
    }


    @GetMapping({"/detail"})
    public Result replayDetail(@RequestParam  Long replayId) {
        GameReplay replay = gameReplayService.selectByPrimaryKey(replayId);
        return successResult(replay);
    }


    @IgnoreToken
    @GetMapping({"/download"})
    public Result download(@RequestParam  Long replayId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        GameReplay replay = gameReplayService.selectByPrimaryKey(replayId);

        if (replay.getReplayContractId() != null){
            //去安存下载

           String path=  AncunUtil.downloadContract("/usr/local/tomcat/webapps/docs/replay",replay.getReplayContractId(),response,request);

            if (StringUtil.isEmpty(path)){
                path = replay.getReplayFile();
            }

            File file = new File(path);
            if (!file.exists()) {
                return (new Result()).resultCode(ResultCode.NO_DATA).detail("replay不存在");
            } else {
                FileUtils.downLoad(file, request, response);
                return this.successResult();
            }
        }else {
            return (new Result()).resultCode(ResultCode.NO_DATA).detail("replay不存在");
        }

    }

}