//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.service;

import com.migo.entity.TbUserEntity;
import java.util.List;

public interface TbUserService {
    List<TbUserEntity> queryList(String var1);

    int changeStatus(long var1, int var3);
}
