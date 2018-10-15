//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.game;

import com.cc.mapper.game.GameReplayMapper;
import com.cc.model.game.GameReplay;
import com.cc.service.BaseService;

import java.util.List;

public interface GameReplayService extends BaseService<GameReplay, GameReplayMapper> {
    /**
     * replay列表
     * @param userId
     * @param gameId
     * @param search
     * @return
     */
    List<GameReplay> findReplays(Long gameId,Long userId,String search);

}
