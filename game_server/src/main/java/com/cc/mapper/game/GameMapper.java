//

//

package com.cc.mapper.game;

import com.cc.mapper.BaseMapper;
import com.cc.model.game.Game;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameMapper extends BaseMapper<Game> {

   List<String> selectAllType();


   List<Game> findObjectsByType(@Param("type") String type,@Param("limit") Integer limit);
}
