package com.cc.model.game;

import lombok.Data;

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
}
