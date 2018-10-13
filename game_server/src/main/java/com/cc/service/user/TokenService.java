//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.service.user;

import com.cc.model.user.TokenModel;
import java.util.Map;

public interface TokenService {
    TokenModel queryByUserId(Long userId);

    TokenModel queryByToken(String token);

    void save(TokenModel token);

    void update(TokenModel token);

    Map<String, Object> createToken(long userId);
}
