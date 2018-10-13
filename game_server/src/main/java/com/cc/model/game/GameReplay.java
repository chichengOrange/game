package com.cc.model.game;

import lombok.Data;

@Data
public class GameReplay {
    private Long id;

    private Long gameId;

    private Long userId;

    private String replayContent;

    private String replayFile;

    private String replayContractId;

    private Short contractStatus;


    private String gameName;

    private String userName;

}