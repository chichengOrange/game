//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.service.impl;

import com.migo.dao.GameDao;
import com.migo.entity.Game;
import com.migo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameService")
public class GameServiceImpl extends BaseServiceImpl<Game, GameDao> implements GameService {
    @Autowired
    private GameDao gameDao;
}
