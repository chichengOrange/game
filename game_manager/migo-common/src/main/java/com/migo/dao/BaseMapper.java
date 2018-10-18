package com.migo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by AAS on 2018/3/29.
 */
public interface BaseMapper<T> {

    int insertSelective(T t);//选择性插入

    int insertBatch(List<Object> tList);//批量插入

    int deleteByPrimaryKey(Long id);

    int deleteBatch(Long[] userIds);

    int updateByPrimaryKeySelective(T t);

    T selectByPrimaryKey(Long id);//用主键查询

    List<T> findObjectsBySearch(@Param("search") String search);

    List<T> selectAllSelective(T t);


}
