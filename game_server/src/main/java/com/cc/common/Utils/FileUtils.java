package com.cc.common.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.common.consts.YmlDefine;
import com.github.pagehelper.util.StringUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.net.util.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class FileUtils {


    public static final String FILEPATH = YmlDefine.wjtFilePath;

    // json写入文件
    public synchronized static void write2File(Object json, String fileName) {

        BufferedWriter writer = null;
        File filePath = new File(FILEPATH);
        JSONObject eJSON = null;

        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdirs();
        }

        File file = new File(FILEPATH + File.separator + fileName + ".xml");
        System.out.println("path:" + file.getPath() + " abs path:" + file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("createNewFile，出现异常:");
                e.printStackTrace();
            }
        } else {
            eJSON = (JSONObject) read2JSON(fileName);
        }

        try {
            writer = new BufferedWriter(new FileWriter(file));

            if (eJSON == null) {
                writer.write(json.toString());
            } else {
                Object[] array = ((JSONObject) json).keySet().toArray();
                for (int i = 0; i < array.length; i++) {
                    eJSON.put(array[i].toString(), ((JSONObject) json).get(array[i].toString()));
                }

                writer.write(eJSON.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 读文件到json
    public static JSONObject read2JSON(String fileName) {
        File file = new File(FILEPATH + File.separator + fileName + ".xml");
        if (!file.exists()) {
            return null;
        }

        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (JSONObject) JSON.parse(laststr);
    }

    // 通过key值获取文件中的value
    public static Object getValue(String fileName, String key) {
        JSONObject eJSON = null;
        eJSON = (JSONObject) read2JSON(fileName);
        if (null != eJSON && eJSON.containsKey(key)) {
            @SuppressWarnings("unchecked")
            Map<String, Object> values = JSON.parseObject(eJSON.toString(), Map.class);
            return values.get(key);
        } else {
            return null;
        }
    }

    public static HashMap<Long, Long> toHashMap(JSONObject js) {
        if (js == null) {
            return null;
        }
        HashMap<Long, Long> data = new HashMap<Long, Long>();
        // 将json字符串转换成jsonObject
        Set<String> set = js.keySet();
        // 遍历jsonObject数据，添加到Map对象
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Long keyLong = Long.valueOf(key);

            String value = js.getString(key);
            Long valueLong;
            if (StringUtil.isEmpty(value)) {
                valueLong = js.getLong(key);
            } else {
                valueLong = Long.valueOf(value);
            }
            data.put(keyLong, valueLong);
        }
        return data;
    }




    public static void uploadFile(MultipartFile file, File saveFile, MessageDigest digest) throws IOException {
        InputStream in = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(saveFile);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        byte[] buffer = new byte[4096];

        int b;
        while((b = in.read(buffer)) != -1) {
            digest.update(buffer, 0, b);
            out.write(buffer, 0, b);
        }

        out.flush();
        out.close();
        fos.close();
        in.close();
    }

    public static byte[] byteByFile(String filePath) {
        byte[] buffer = null;

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            int n;
            while((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            bos.flush();
            buffer = bos.toByteArray();
            fis.close();
            bos.close();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return buffer;
    }

    public static void downLoad(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = file.getName();
        String agent = request.getHeader("User-Agent");
        String filenameEncoder = "";
        if (agent.contains("MSIE")) {
            filenameEncoder = URLEncoder.encode(filename, "utf-8");
            filenameEncoder = filenameEncoder.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            filenameEncoder = "=?utf-8?B?" + Base64.encodeBase64(filename.getBytes("utf-8")) + "?=";
        } else {
            filenameEncoder = URLEncoder.encode(filename, "utf-8");
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filenameEncoder);
        String path = file.getAbsolutePath();
        InputStream in = new FileInputStream(path);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream buff = new BufferedInputStream(in);
        byte[] b = new byte[2048];
        long l = 0L;

        while(l < file.length()) {
            int j = buff.read(b, 0, 2048);
            l += (long)j;
            out.write(b, 0, j);
        }

        buff.close();
        in.close();
        out.close();
    }

}
