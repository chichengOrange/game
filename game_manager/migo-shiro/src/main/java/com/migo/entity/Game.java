//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.entity;

import java.util.Date;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
public class Game {
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

}
