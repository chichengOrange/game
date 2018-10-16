//

//

package com.cc.mapper.game;

import com.cc.mapper.BaseMapper;
import com.cc.model.game.Game;

import java.util.List;

public interface GameMapper extends BaseMapper<Game> {

   List<String> selectAllType();
}
