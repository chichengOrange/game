//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.service.impl;

import com.github.pagehelper.PageHelper;
import com.migo.dao.BaseMapper;
import com.migo.result.PageResult;
import com.migo.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseServiceImpl<O, M extends BaseMapper> implements BaseService<O, M> {
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
        return (O)m.selectByPrimaryKey(id);
    }

    public PageResult findListByPage(int pageNum, int pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<O> sss = this.m.findObjectsBySearch(search);
        return new PageResult(sss);
    }

    public List<O> selectAll() {
        return this.m.selectAllSelective((Object)null);
    }

    public PageResult findListByPage(int pageNum, int pageSize, String search, String sidx, String order) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotEmpty(sidx) && StringUtils.isNotEmpty(order)) {
            PageHelper.orderBy(sidx + " " + order);
        }

        List<O> sss = this.m.findObjectsBySearch(search);
        return new PageResult(sss);
    }
}
