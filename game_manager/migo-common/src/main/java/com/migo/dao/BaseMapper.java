//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T> {
    int insertSelective(T var1);

    int insertBatch(List<Object> var1);

    int deleteByPrimaryKey(Long var1);

    int deleteBatch(Long[] var1);

    int updateByPrimaryKeySelective(T var1);

    T selectByPrimaryKey(Long var1);

    List<T> findObjectsBySearch(@Param("search") String var1);

    List<T> selectAllSelective(T var1);
}
