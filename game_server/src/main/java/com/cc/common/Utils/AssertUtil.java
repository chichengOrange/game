package com.cc.common.Utils;

import org.apache.commons.lang3.StringUtils;

public class AssertUtil {
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }

    public static void isPhone(String phone) {
        if (!CheckerUtil.isPhone(phone)) {
            throw new RRException("电话格式错误");
        }
    }

    public static void isEmail(String email) {
        if (!CheckerUtil.isEmail(email)) {
            throw new RRException("邮件格式错误");
        }
    }

    public static void isIdentity(String identity) {
        if (!CheckerUtil.isIdentity(identity)) {
            throw new RRException("身份证格式错误");
        }
    }
}
