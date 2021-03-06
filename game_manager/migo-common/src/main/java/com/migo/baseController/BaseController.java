//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.baseController;

import com.migo.enums.ResultCode;
import com.migo.result.Result;
import com.migo.utils.RRException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {
    }

    @ExceptionHandler({Exception.class})
    public Result handler(HttpServletRequest req, Exception e) {
        String url = req.getRequestURI() + "===";
        this.logger.info(url + " Http请求发生异常...");
        String erMsg = e.getMessage();
        Result result = new Result();
        this.logger.error(url + erMsg, e);
        if (e instanceof RRException) {
            return result.fail(((RRException)e).getCode(), ((RRException)e).getMsg(), (String)null);
        } else if (e instanceof MissingServletRequestParameterException) {
            return result.fail(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getMsg(), erMsg);
        } else if (e instanceof IllegalArgumentException) {
            return result.fail(302, "数据类型不匹配", erMsg);
        } else if (!(e instanceof SQLException) && !(e instanceof BadSqlGrammarException)) {
            if (e instanceof DuplicateKeyException) {
                return result.fail(ResultCode.EXIST.getCode(), e.getCause().getMessage().replace("Duplicate entry", "").replace("'", "") + "  已存在", erMsg);
            } else {
                return e instanceof DataIntegrityViolationException ? result.fail(ResultCode.EXIST.getCode(), "外键约束故障", erMsg) : result.fail(ResultCode.FAIL.getCode(), "网络故障", erMsg);
            }
        } else {
            return result.fail(303, "数据库访问异常", erMsg);
        }
    }
}
