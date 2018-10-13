package com.cc.mapper.user;

import com.cc.mapper.BaseMapper;
import com.cc.model.user.TokenModel;

public interface TokenMapper extends BaseMapper<TokenModel> {
    TokenModel queryByUserId(Long userId);

    TokenModel queryByToken(String token);
}
