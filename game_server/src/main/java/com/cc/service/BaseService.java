//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service;

import com.cc.common.result.PageResult;
import com.cc.common.result.Result;
import java.util.List;

public interface BaseService<O, M> {
    int insertSelective(O o);

    default int insertBatch(List<O> oList) {
        return 0;
    }

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(O o);

    O selectByPrimaryKey(Long id);

    PageResult findListByPage(int pageNum, int pageSize, String search);

    Result selectAllSelective(int pageNum, O o, String order);

    List<O> selectAll();
}
