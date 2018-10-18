package com.migo.result;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.migo.enums.ResultCode;

/**
 * Created by AAS on 2018/3/20.
 */
public class Result<T> {

    private int code;

    private String message;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty)
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
        if (r == 0) {
            return new Result(ResultCode.SAVEFAIL, null);
        } else {
            return new Result(ResultCode.SUCCESS, null);
        }
    }


    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }


    public Result data(T data) {
        this.data = data;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
