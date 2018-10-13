package com.cc.common.Utils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author jerry.chen
 * @date 2018/8/3 10:23
 **/
public class MyUtil {


    public static void setHeadByResponse(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cahce-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/json; charset=utf-8");

        //options
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE,PUT,PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, x-requested-with, X-Custom-Header, Authorization,cache-control");
    }

    /**
     * 获取文件大小 转化为kb MB GB 等
     *
     * @param file  文件
     * @param scale 保留几位小数
     * @return
     */
    public static String getFileSize(File file, int scale) {
        long total = file.length();
        String units = "b";//单位名称

        if (total / 1024 > 1) {
            total = total / 1024;
            units = "kb";
        }
        if (total / 1024 > 1) {
            total = total / 1024;
            units = "MB";
        }

        if (total / 1024 > 1) {
            total = total / 1024;
            units = "GB";
        }

        BigDecimal bigDecimal = new BigDecimal(total);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);

        return bigDecimal.doubleValue() + units;
    }


    /**
     * 计算文件的 MD5 值
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
