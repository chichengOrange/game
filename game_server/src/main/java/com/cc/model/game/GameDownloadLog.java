package com.cc.model.game;

import lombok.Data;

import java.util.Date;

@Data
public class GameDownloadLog {
    private Long gameId;

    private Long userId;

    private Date downloadTime;

    private String ip;

    private Integer source;


}