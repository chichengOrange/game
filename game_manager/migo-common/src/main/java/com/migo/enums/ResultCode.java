//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.enums;

public enum ResultCode {
    SUCCESS(0, "SUCCESS"),
    NO_DATA(201, "暂无数据"),
    EXIST(23000, "数据已存在"),
    UPLOADFAIL(300, "上传失败"),
    SAVEFAIL(300, "操作失败"),
    PASSWORD_ERROR(308, "用户名或密码错误"),
    PARAMETER_ERROR(306, "参数错误"),
    TOKEN_ERROR(301, "token无效"),
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
