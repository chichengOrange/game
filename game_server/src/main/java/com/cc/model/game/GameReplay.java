package com.cc.model.game;

import com.hg.xdoc.XDocService;
import lombok.Data;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class GameReplay {
    private Long id;

    private Long gameId;

    private Long userId;

    private String replayContent;

    private String replayFile;

    private String visaFile;

    private String replayContractId;

    private Short contractStatus;


    private String gameName;

    private String userName;

    private Date createTime;

    public static void main(String[] args) {
        XDocService xdocService = new XDocService("http://www.xdocin.com", "");
        Map<String, Object> param = new HashMap<>();
        param.put("公司名称", "北京小米科技有限责任公司");
        param.put("外文名称", "MI");
        param.put("总部地点", "北京市海淀区清河中街68号");
        param.put("成立时间", 2010);
        param.put("徽标", "http://www.xdocin.com/demo/xiaomi.gif");
        param.put("公司口号", "探索黑科技，小米为发烧而生");
        param.put("年营业额", 780);
        param.put("员工数", 14000);
        param.put("CEO", "雷军");
        try {
            xdocService.run("http://www.xdocin.com/demo/card.docx",//模板文件
                    param,
                    new File("D:/XDocResult.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}