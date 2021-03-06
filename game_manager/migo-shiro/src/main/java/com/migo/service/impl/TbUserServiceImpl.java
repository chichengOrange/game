//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.service.impl;

import com.migo.dao.TbUserDao;
import com.migo.entity.TbUserEntity;
import com.migo.service.TbUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbUserService")
public class TbUserServiceImpl implements TbUserService {
    @Autowired
    private TbUserDao tbUserDao;

    public TbUserServiceImpl() {
    }

    public List<TbUserEntity> queryList(String search) {
        return this.tbUserDao.queryBySearch(search);
    }

    public int changeStatus(long userId, int status) {
        return this.tbUserDao.changeStatus(userId, status);
    }
}
