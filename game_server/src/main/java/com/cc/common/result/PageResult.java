//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> extends Result {
    int totalCount;
    int totalPage;
    int pageSize;
    int currentPage;

    public PageResult(List<T> t) {
        this.setCode(200);
        this.setMessage("SUCCESS");
        this.setData(t);
        PageInfo<T> pageInfo = new PageInfo(t);
        this.totalCount = Integer.valueOf(String.valueOf(pageInfo.getTotal()));
        this.totalPage = pageInfo.getPages();
        this.currentPage = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

}
