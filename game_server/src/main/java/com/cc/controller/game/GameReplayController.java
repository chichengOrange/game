package com.cc.controller.game;

import com.ancun.netsign.model.NetSignResponse;
import com.cc.common.Utils.AncunUtil;
import com.cc.common.Utils.FileTypeUtils;
import com.cc.common.Utils.FileUtils;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.consts.YmlDefine;
import com.cc.common.enums.ResultCode;
import com.cc.common.incCode.IncNoService;
import com.cc.common.incCode.IncTypeEnum;
import com.cc.common.interceptor.MyAdapterInterceptor;
import com.cc.common.result.PageResult;
import com.cc.common.result.Result;
import com.cc.controller.BaseController;
import com.cc.model.game.GameReplay;
import com.cc.model.user.UserModel;
import com.cc.service.game.GameReplayService;
import com.cc.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/gameReplay"})
public class GameReplayController extends BaseController {


    @Autowired
    private GameReplayService gameReplayService;

    @Autowired
    private IncNoService incNoService;

    @Autowired
    private UserService userService;


    /**
     * replay列表
     *
     * @param page     第几页
     * @param pageSize 每页条数
     * @param userId   用户id
     * @param gameId   游戏id
     * @param sidx     排序字段
     * @param order    升序还是降序
     * @param search   搜索条件
     * @return
     */
    @IgnoreToken
    @GetMapping({"/list"})
    public PageResult replayList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                 Long userId, Long gameId, String sidx, String order, String search) {

        PageHelper.startPage(page, pageSize);
        if (StringUtils.isNotEmpty(sidx) && StringUtils.isNotEmpty(order)) {
            PageHelper.orderBy(sidx + " " + order);
        }

        List<GameReplay> gameReplays = gameReplayService.findReplays(gameId, userId, search);
        return new PageResult(gameReplays);
    }


    @GetMapping({"/detail"})
    public Result replayDetail(@RequestParam Long replayId) {
        GameReplay replay = gameReplayService.selectByPrimaryKey(replayId);
        return successResult(replay);
    }


    @IgnoreToken
    @GetMapping({"/download"})
    public Result download(@RequestParam Long replayId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        GameReplay replay = gameReplayService.selectByPrimaryKey(replayId);

        if (replay.getReplayContractId() != null) {
            //去安存下载
            String path = AncunUtil.downloadContract(YmlDefine.baseFilePath + "replay/", replay.getReplayContractId());

            if (StringUtil.isEmpty(path)) {
                path = replay.getVisaFile();
            }

            File file = new File(path);
            if (!file.exists()) {
                return (new Result()).resultCode(ResultCode.NO_DATA).detail("visa文件不存在");
            } else {
                FileUtils.downLoad(file, request, response);
                return this.successResult();
            }
        } else {
            return (new Result()).resultCode(ResultCode.NO_DATA).detail("visa文件不存在");
        }
    }


    /**
     * @param gameId     游戏ID
     * @param pdfFile    记录学生成绩 存储在安存
     * @param replayFile 游戏记录上传
     * @param request
     * @param replayContent 记录内容
     * @return
     * @throws IOException
     */

    @PostMapping({"/upload"})
    public Result upload(@RequestParam Long gameId, MultipartFile pdfFile,
                         @RequestParam MultipartFile replayFile,
                         String replayContent,
                         HttpServletRequest request) throws IOException {
        Object userO = request.getAttribute(MyAdapterInterceptor.LOGIN_USER_KEY);

        if (userO == null || !NumberUtils.isDigits(userO.toString())) {
            return failResult().message("token失效");
        }

        Long userId = Long.valueOf(userO.toString());

        Long id = incNoService.nextIdByType(IncTypeEnum.REPLAY_ID);

        GameReplay replay = new GameReplay();
        replay.setGameId(gameId);
        replay.setId(id);
        replay.setUserId(userId);

        String bathPath = YmlDefine.baseFilePath;

        if (pdfFile!=null && !pdfFile.isEmpty()) {
            if (!"pdf".equals(FileTypeUtils.getFileType(pdfFile.getInputStream()))) {
                return failResult().message("pdf文件格式不对");
            }
            File file = new File(bathPath + "replay/" + id + ".pdf");
            FileUtils.uploadFile(pdfFile, file);
            replay.setVisaFile(file.getPath());

            UserModel userModel = userService.selectByPrimaryKey(userId);

            if (userModel.getRnameStatus() != null && userModel.getRnameStatus() == 1) {
                NetSignResponse netSignResponse = AncunUtil.initiatingContractCustomFile(id.toString(), id.toString()+".pdf", pdfFile.getInputStream(), userModel.getRealname(), userModel.getIdentity());

                if (netSignResponse.getCode() == 100000) {
                    replay.setReplayContractId(id.toString());
                }
            }
        }

        File saveFile = new File(bathPath + "replay/" + replayFile.getOriginalFilename());
        FileUtils.uploadFile(replayFile, saveFile);
        replay.setReplayFile(saveFile.getPath());
        replay.setCreateTime(new Date());
        replay.setReplayContent(replayContent);
        int r = gameReplayService.insertSelective(replay);
        return saveOrUpdateResult(r);
    }

}