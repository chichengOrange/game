//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> extends Result {
    private long totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;

    public PageResult(List<T> t) {
        this.setCode(200);
        this.setMessage("SUCCESS");
        this.setData(t);
        PageInfo<T> pageInfo = new PageInfo(t);
        this.totalCount = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.currPage = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }




}
