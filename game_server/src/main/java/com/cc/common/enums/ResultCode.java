//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.enums;

public enum ResultCode {
    SUCCESS(200, "SUCCESS"),
    NO_DATA(201, "暂无数据"),
    EXIST(23000, "数据已存在"),
    TOKEN_ERROR(301, "token无效"),
    PASSWORD_ERROR(308, "用户名或密码错误"),
    PARAMETER_ERROR(306, "参数错误"),
    UPLOAD_FAIL(300, "上传失败"),
    SAVE_FAIL(300, "操作失败"),
    FAIL(500, "网络故障");

    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
