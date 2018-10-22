//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.migo.enums.ResultCode;

public class Result<T> {
    private int code;
    private String message;
    @JSONField(
            serialzeFeatures = {SerializerFeature.WriteNullStringAsEmpty}
    )
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

    public static Result saveOrUpdate(int r) {
        return r == 0 ? new Result(ResultCode.SAVEFAIL, (Object)null) : new Result(ResultCode.SUCCESS, (Object)null);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
