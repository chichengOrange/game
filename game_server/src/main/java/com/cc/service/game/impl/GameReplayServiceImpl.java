//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.game.impl;

import com.cc.mapper.game.GameReplayMapper;
import com.cc.model.game.GameReplay;
import com.cc.service.BaseServiceImpl;
import com.cc.service.game.GameReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gameReplayService")
public class GameReplayServiceImpl extends BaseServiceImpl<GameReplay, GameReplayMapper> implements GameReplayService {
    @Autowired
    private GameReplayMapper gameReplayMapper;

    @Override
    public List<GameReplay> findReplays(Long gameId, Long userId, String search) {

        List<GameReplay> replays=null;

        if (gameId != null){
            replays=gameReplayMapper.findObjectsByGameId(gameId,search);
        }else if(userId != null){
           replays=gameReplayMapper.findObjectsByUserId(userId,search);
        }else {
            replays = gameReplayMapper.findObjectsBySearch(search);
        }

        return replays;
    }
}