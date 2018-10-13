package com.cc.common.Utils;

import com.cc.common.consts.GlobDefine;
import com.cc.common.consts.YmlDefine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author jerry.chen
 * @date 2018/7/30 16:06
 **/
public class TokenSignUtil {

    /**
     * 生成并保存token
     *
     * @param redisUtil
     * @param user_id
     * @return
     */
    public static String generateAndSaveToken(RedisUtil redisUtil, String user_id) {

        String encodeUserId = AesUtil.encode(user_id);

        String token = encodeUserId + YmlDefine.tokenFlag + UUID.randomUUID().toString();

        redisUtil.set(GlobDefine.redisTokenPrefix + encodeUserId, token, 30 * 24 * 3600);//超时30天

        return token;
    }

    /**
     * 判断token是否可用
     */
    public static boolean isAvailableToken(RedisUtil redisUtil, String token) {

        String encodeUserId = token.substring(0, token.indexOf(YmlDefine.tokenFlag));

        Object redisValue = redisUtil.get(GlobDefine.redisTokenPrefix + encodeUserId);


        if (redisValue == null || !redisValue.toString().trim().equals(token.trim())) {
            return false;
        }
        return true;
    }


    /**
     * 根据参数获取签名
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        String[] keys = keysSet.toArray(new String[keysSet.size()]);
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (String key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }

            temp.append(valueString);
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }

        return MD5Encode.encryption(temp.toString());
    }

}
