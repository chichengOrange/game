//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.Utils;

import java.io.Serializable;

public class RRException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -601494750574444665L;
    private String msg;
    private int code = 500;

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RRException)) {
            return false;
        } else {
            RRException other = (RRException)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg == null) {
                        return this.getCode() == other.getCode();
                    }
                } else if (this$msg.equals(other$msg)) {
                    return this.getCode() == other.getCode();
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RRException;
    }


    public String toString() {
        return "RRException(msg=" + this.getMsg() + ", code=" + this.getCode() + ")";
    }
}
