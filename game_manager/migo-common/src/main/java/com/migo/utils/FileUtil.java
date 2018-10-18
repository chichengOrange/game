package com.migo.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author jerry.chen
 * @date 2018/9/26 9:33
 **/
public class FileUtil {

    /**
     * 上传文件
     *
     * @param file
     * @param saveFilePath
     * @param
     * @throws IOException
     */
    public static void uploadFile(MultipartFile file, String saveFilePath) throws IOException {

        //MessageDigest digest  计算md5
        File saveFile = new File(saveFilePath);

        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        InputStream in = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(saveFile);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        // out.write(file.getBytes()); 直接写大文件可能内存溢出
        int b;
        byte[] buffer = new byte[4096];


        while ((b = in.read(buffer)) != -1) {
            //digest.update(buffer, 0, b);
            out.write(buffer, 0, b);
        }
        out.flush();
        out.close();
        fos.close();
        in.close();
    }


    /**
     * 下载文件
     *
     * @param file
     * @param request
     * @param response
     * @throws IOException
     */
    public static void download(File file, HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        String filename = file.getName();

        String agent = request.getHeader("User-Agent");
        // 根据不同的客户端进行不同的编码
        String filenameEncoder = "";
        if (agent.contains("MSIE")) {
            // IE浏览器
            filenameEncoder = URLEncoder.encode(filename, "utf-8");
            filenameEncoder = filenameEncoder.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filenameEncoder = "=?utf-8?B?" + Base64.encodeBase64(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filenameEncoder = URLEncoder.encode(filename, "utf-8");
        }

        // 要下载的这个文件的类型-------客户端通过文件的MIME类型去区分类型
        response.setContentType("application/octet-stream");
        // 告诉客户端该文件不是直接解析，而是以附件形式打开（下载）
        response.setHeader("Content-Disposition", "attachment;filename=" + filenameEncoder);

        // 获取文件的绝对路径
        String path = file.getAbsolutePath();
        // 获得该文件的输入流
        InputStream in = new FileInputStream(path);
        // 获得输出流--通过response获得的输出流 用于向客户端写内容
        ServletOutputStream out = response.getOutputStream();


        // 方法1-1：IO字节流下载，用于小文件
       /* int len ;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
*/  //小文件


        //方法1-2：IO字符流下载，用于大文件 下边为大文件 创建文件缓冲输入流
        BufferedInputStream buff = new BufferedInputStream(in);
        //设置每次读取大小
        byte[] b = new byte[2048];
        //用于判断是否等同于文件的长度，即文件下载大小
        long l = 0;
        //循环读取文件
        while (l < file.length()) {
            int j = buff.read(b, 0, 2048);
            //使用缓冲流读取数据,返回缓冲区长度
            l += j;
            //将缓存写入客户端
            out.write(b, 0, j);
        }

        buff.close();


        in.close();
        out.close();
    }


}
