//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.service;

import com.migo.result.PageResult;
import java.util.List;

public interface BaseService<O, M> {
    int insertSelective(O var1);

    default int insertBatch(List<O> oList) {
        return 0;
    }

    int deleteByPrimaryKey(Long var1);

    int updateByPrimaryKeySelective(O var1);

    O selectByPrimaryKey(Long var1);

    PageResult findListByPage(int var1, int var2, String var3);

    List<O> selectAll();

    PageResult findListByPage(int var1, int var2, String var3, String var4, String var5);
}
