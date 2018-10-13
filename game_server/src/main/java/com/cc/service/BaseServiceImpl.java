//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service;

import com.cc.common.result.PageResult;
import com.cc.common.result.Result;
import com.cc.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<O, M extends BaseMapper> implements BaseService<O, M> {
    @Autowired
    private M m;

    public BaseServiceImpl() {
    }

    public int insertSelective(O o) {
        return this.m.insertSelective(o);
    }

    public int insertBatch(List<O> os) {
        return this.m.insertBatch(os);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.m.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(O o) {
        return this.m.updateByPrimaryKeySelective(o);
    }

    public O selectByPrimaryKey(Long id) {
        return (O) this.m.selectByPrimaryKey(id);
    }

    public PageResult findListByPage(int pageNum, int pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<O> sss = this.m.findObjectsBySearch(search);
        return new PageResult(sss);
    }

    public Result selectAllSelective(int pageNum, O o, String order) {
        PageHelper.startPage(pageNum, 10);
        if (StringUtil.isNotEmpty(order)) {
            PageHelper.orderBy(order);
        }

        List<O> sss = this.m.selectAllSelective(o);
        return new PageResult(sss);
    }

    public List<O> selectAll() {
        return this.m.selectAllSelective((Object)null);
    }
}
