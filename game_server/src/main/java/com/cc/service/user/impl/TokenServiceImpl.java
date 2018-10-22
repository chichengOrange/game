//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user.impl;

import com.cc.mapper.user.TokenMapper;
import com.cc.model.user.TokenModel;
import com.cc.service.BaseServiceImpl;
import com.cc.service.user.TokenService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tokenService")
public class TokenServiceImpl extends BaseServiceImpl<TokenModel,TokenMapper> implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;
    private static final int EXPIRE = 3600*12*10;//10天

    public TokenServiceImpl() {
    }

    public TokenModel queryByUserId(Long userId) {
        return this.tokenMapper.queryByUserId(userId);
    }

    public TokenModel queryByToken(String token) {
        return this.tokenMapper.queryByToken(token);
    }

    public void save(TokenModel token) {
        this.tokenMapper.insertSelective(token);
    }

    public void update(TokenModel token) {
        this.tokenMapper.updateByPrimaryKeySelective(token);
    }

    public Map<String, Object> createToken(long userId) {
        String token = UUID.randomUUID().toString();
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + EXPIRE*1000);
        TokenModel tokenModel = this.queryByUserId(userId);
        if (tokenModel == null) {
            tokenModel = new TokenModel();
            tokenModel.setUserId(userId);
            tokenModel.setToken(token);
            tokenModel.setUpdateTime(now);
            tokenModel.setExpireTime(expireTime);
            this.save(tokenModel);
        } else {
            tokenModel.setToken(token);
            tokenModel.setUpdateTime(now);
            tokenModel.setExpireTime(expireTime);
            this.update(tokenModel);
        }

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("expire", EXPIRE);
        return map;
    }
}
