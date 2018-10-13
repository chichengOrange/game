package com.cc.mapper.game;

import com.cc.mapper.BaseMapper;
import com.cc.model.game.GameReplay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameReplayMapper extends BaseMapper<GameReplay> {

    List<GameReplay> findObjectsByGameId(@Param("gameId") Long gameId, @Param("search") String search);
    List<GameReplay> findObjectsByUserId(@Param("userId") Long userId,@Param("search") String search);

}