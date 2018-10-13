package com.cc.controller;

import com.cc.common.enums.ResultCode;
import com.cc.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by AAS on 2018/3/20.
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public Result handler(HttpServletRequest req, Exception e) {

        String url = req.getRequestURI() + "===";

        logger.info(url + " Http请求发生异常...");

        String erMsg = e.getMessage();

        Result result = new Result();

        logger.error(url + erMsg, e);


        /*else if (e instanceof NullPointerException) {
            return result.fail(500, "发生空指针异常", erMsg);
        }*/

        if (e instanceof MissingServletRequestParameterException) {
            return result.fail(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getMsg(), erMsg);
        } else if (e instanceof IllegalArgumentException) {
            return result.fail(302, "数据类型不匹配", erMsg);
        } else if (e instanceof SQLException || e instanceof BadSqlGrammarException) {
            return result.fail(303, "数据库访问异常", erMsg);
        } else if (e instanceof DuplicateKeyException) {
            return result.fail(ResultCode.EXIST.getCode(), e.getCause().getMessage().replace("Duplicate entry", "").replace("'", "") + "  已存在", erMsg);
        } else if (e instanceof DataIntegrityViolationException) {
            return result.fail(ResultCode.EXIST.getCode(), "外键约束故障", erMsg);
        } else {
            return result.fail(ResultCode.FAIL.getCode(), "网络故障", erMsg);
        }

    }

    public Result successResult() {
        return this.successResult(null);
    }

    public Result successResult(Object o) {
        Result result = new Result(ResultCode.SUCCESS, o);
        return result;
    }

    public Result failResult() {
        Result result = new Result(ResultCode.SAVE_FAIL, null);
        return result;
    }


    public Result saveOrUpdateResult(int result) {
        //TODO
        if (result > 0)
            return successResult();
        else
            return failResult();
    }


}
