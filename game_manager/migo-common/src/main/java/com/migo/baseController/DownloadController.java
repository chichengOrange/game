//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.baseController;

import com.migo.enums.ResultCode;
import com.migo.result.Result;
import com.migo.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {
    public DownloadController() {
    }

    @GetMapping({"/download"})
    public Result downLoad(@RequestParam String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return (new Result()).resultCode(ResultCode.NO_DATA).detail("文件不存在");
        } else {
            FileUtil.download(file, request, response);
            return Result.success((Object)null);
        }
    }
}
