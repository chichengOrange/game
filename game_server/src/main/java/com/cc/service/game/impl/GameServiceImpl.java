//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.game.impl;

import com.cc.mapper.game.GameDownloadLogMapper;
import com.cc.mapper.game.GameMapper;
import com.cc.model.game.Game;
import com.cc.model.game.GameDownloadLog;
import com.cc.service.BaseServiceImpl;
import com.cc.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gameService")
public class GameServiceImpl extends BaseServiceImpl<Game, GameMapper> implements GameService {
    @Autowired
     private GameMapper gameMapper;

    @Autowired
    private GameDownloadLogMapper gameDownloadLogMapper;
    @Override
    public List<String> selectAllType(){
        return gameMapper.selectAllType();
    }


    @Override
    public int insertDownloadLog(GameDownloadLog gameDownloadLog){
       return gameDownloadLogMapper.insertSelective(gameDownloadLog);
    }

    @Override
    public List<Game> findObjectsByType(String type, Integer limit) {
        return gameMapper.findObjectsByType(type,limit);
    }
}
