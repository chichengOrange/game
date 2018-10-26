package com.cc.common.consts;

import com.cc.common.Utils.YmlUtil;

/**
 * @author jerry.chen
 * @date 2018/8/1 17:37
 **/
public class YmlDefine {

    /**
     * baseFilePath
     */
    public static final String baseFilePath = YmlUtil.getConfigByKey("baseFilePath");


    /**
     * aes钥匙
     */
    public static final String aesSecret = YmlUtil.getConfigByKey("aesSecret");


    /**
     * user_id 和token拼接中间用的flag 用来分割字符串
     */
    public static final String tokenFlag = YmlUtil.getConfigByKey("flag");


    /**
     * 用来转换静态文件的url路径 需要改Tomcat的 server.xml
     */
    public static final String urlPath = YmlUtil.getConfigByKey("urlPath");


    /**
     * word文档模板路径
     */
    public static final String docTemPath = YmlUtil.getConfigByKey("docTemPath");


}
