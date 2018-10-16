//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.result;

import com.cc.common.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Result<T> {
    private int code;
    private String message;
    private String detail;
    private T data;

    public Result() {
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    public Result fail(int status, String msg, String detail) {
        this.code = status;
        this.message = msg;
        this.detail = detail;
        return this;
    }

    public Result code(int code) {
        this.code = code;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result detail(String detail) {
        this.detail = detail;
        return this;
    }

    public Result resultCode(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMsg();
        return this;
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }


}
