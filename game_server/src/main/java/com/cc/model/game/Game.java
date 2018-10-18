package com.cc.model.game;

import lombok.Data;

import java.io.*;
import java.util.Date;

@Data
public class Game {
    private Long id;
    private String name;
    private String title;
    private String description;
    private String type;
    private String version;
    private String appPackage;
    private String appLocation;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private String history;
    private String set;
    private String picture;
    private String video;


    /**
     * 非数据库字段 根据game_download_log 表count得出
     */
    private Integer downloadCount;




    public static void main(String[] args) throws IOException {
        File f=new File("C:\\Users\\Administrator\\Documents\\WeChat Files\\hnndcck\\Files\\wangyi.sav");
        InputStream in= new FileInputStream(f);
        OutputStream out=new FileOutputStream(new File("D:\\b.pdf"));
        int i=0;
        int j=0;
        char[] c =new char[(int) f.length()];
        while((i=in.read())!=-1){
            c[j++]=(char)i;
        }
        String str=new String(c);
        System.out.println(str);
        out.write(str.getBytes());
        in.close();out.close();
    }

}
