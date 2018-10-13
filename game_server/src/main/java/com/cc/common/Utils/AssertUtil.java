package com.cc.common.Utils;

import org.apache.commons.lang3.StringUtils;

public class AssertUtil {
    public AssertUtil() {
    }

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
}
