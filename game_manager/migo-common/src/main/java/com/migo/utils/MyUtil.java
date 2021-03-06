//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MyUtil {
    public MyUtil() {
    }

    public static void setHeadByResponse(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cahce-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT,PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization,cache-control");
    }

    public static String getFileSize(File file, int scale) {
        long total = file.length();
        String units = "b";
        if (total / 1024L > 1L) {
            total /= 1024L;
            units = "kb";
        }

        if (total / 1024L > 1L) {
            total /= 1024L;
            units = "MB";
        }

        if (total / 1024L > 1L) {
            total /= 1024L;
            units = "GB";
        }

        BigDecimal bigDecimal = new BigDecimal(total);
        bigDecimal = bigDecimal.setScale(scale, 4);
        return bigDecimal.doubleValue() + units;
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        } else {
            MessageDigest digest = null;
            FileInputStream in = null;
            byte[] buffer = new byte[8192];

            String var6;
            try {
                digest = MessageDigest.getInstance("MD5");
                in = new FileInputStream(file);

                int len;
                while((len = in.read(buffer)) != -1) {
                    digest.update(buffer, 0, len);
                }

                BigInteger bigInt = new BigInteger(1, digest.digest());
                var6 = bigInt.toString(16);
                return var6;
            } catch (Exception var16) {
                var16.printStackTrace();
                var6 = null;
            } finally {
                try {
                    in.close();
                } catch (Exception var15) {
                    var15.printStackTrace();
                }

            }

            return var6;
        }
    }
}
