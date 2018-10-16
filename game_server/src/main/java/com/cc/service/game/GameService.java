//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.game;

import com.cc.mapper.game.GameMapper;
import com.cc.model.game.Game;
import com.cc.service.BaseService;

import java.util.List;

public interface GameService extends BaseService<Game, GameMapper> {
    List<String> selectAllType();
}
