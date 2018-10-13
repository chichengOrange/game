package com.cc.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AAS on 2018/3/29.
 */
public interface BaseMapper<T> {

    int insertSelective(T t);

    int insertBatch(List<Object> tList);

    int deleteByPrimaryKey(Long id);

    int deleteBatch(Long[] userIds);

    int updateByPrimaryKeySelective(T t);

    T selectByPrimaryKey(Long id);

    List<T> findObjectsBySearch(@Param("search") String search);

    List<T> selectAllSelective(T t);

}
