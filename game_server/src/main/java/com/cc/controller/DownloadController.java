//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.controller;

import com.cc.common.Utils.FileUtils;
import com.cc.common.annotation.IgnoreToken;
import com.cc.common.enums.ResultCode;
import com.cc.common.result.Result;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController extends BaseController {

    @IgnoreToken
    @ApiOperation(value = "文件下载", notes = "path 为文件路径")
    @GetMapping({"/download"})
    public Result downLoad(@RequestParam String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return (new Result()).resultCode(ResultCode.NO_DATA).detail("文件不存在");
        } else {
            FileUtils.downLoad(file, request, response);
            return this.successResult();
        }
    }
}
