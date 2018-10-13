//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.consts;

import com.google.gson.Gson;
import java.io.File;

public class GlobDefine {
    public static final String redisTokenPrefix = "user:token:";
    public static Gson gson = new Gson();
    public static final int PAGE_SIZE = 10;
    public static final String VALUE_STRING = "value";
    public static final String KEY_STRING = "key";
    public static final String SPLASH_STRING = "/";
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String SEPARATOR;
    public static final int TWO_INT = 2;
    public static final int THREE_INT = 3;
    public static final String DOT_SIGN = ".";
    public static final String NEGATIVE_DOT_SIGN = "-.";
    public static final String LOCAL_FILE_URL = "file:";
    public static final String SPACE = " ";
    public static final String SCRIPT_FILTER_PATTERN = "<script.*?>.*?</script>";
    public static final String USER_DESKTOP;

    private GlobDefine() {
    }

    static {
        SEPARATOR = File.separator;
        USER_DESKTOP = USER_HOME + SEPARATOR + "Desktop";
    }
}
