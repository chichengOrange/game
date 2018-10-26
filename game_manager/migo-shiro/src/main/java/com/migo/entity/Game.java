//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.migo.utils.LongToStringSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
public class Game {
/*
    @JSONField(serializeUsing = LongToStringSerializer.class)
*/
    private Long id;
    @NotBlank(message = "name 不能为空")
    private String name;

    @NotBlank(message = "title 不能为空")
    private String title;

    @NotBlank(message = "description 不能为空")
    private String description;

    @NotBlank(message = "type 不能为空")
    private String type;
    private String version;
    private String appPackage;
    private String appLocation;
    private Long creator;
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

}
