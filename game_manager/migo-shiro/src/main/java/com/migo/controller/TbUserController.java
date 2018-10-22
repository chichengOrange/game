//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.controller;

import com.github.pagehelper.PageHelper;
import com.migo.annotation.SysLog;
import com.migo.baseController.BaseController;
import com.migo.entity.TbUserEntity;
import com.migo.result.PageResult;
import com.migo.result.Result;
import com.migo.service.TbUserService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sys/tbUser"})
public class TbUserController extends BaseController {
    @Autowired
    private TbUserService tbUserService;

    public TbUserController() {
    }

    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:tbUser:list"})
    public PageResult list(String search) {
        PageHelper.startPage(1, 10);
        List<TbUserEntity> list = this.tbUserService.queryList(search);
        return new PageResult(list);
    }

    @SysLog("客户端用户状态修改")
    @RequestMapping({"/changeStatus"})
    public Result changeStatus(@RequestParam long userId, @RequestParam int status) {
        int r = this.tbUserService.changeStatus(userId, status);
        return Result.saveOrUpdate(r);
    }
}
