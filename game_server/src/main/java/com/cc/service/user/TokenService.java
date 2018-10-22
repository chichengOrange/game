//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user;

import com.cc.mapper.user.TokenMapper;
import com.cc.model.user.TokenModel;
import com.cc.service.BaseService;

import java.util.Map;

public interface TokenService extends BaseService<TokenModel,TokenMapper> {
    TokenModel queryByUserId(Long userId);

    TokenModel queryByToken(String token);

    void save(TokenModel token);

    void update(TokenModel token);

    Map<String, Object> createToken(long userId);
}
