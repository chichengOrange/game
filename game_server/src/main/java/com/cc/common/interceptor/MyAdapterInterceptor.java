//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.interceptor;

import com.cc.common.Utils.MyUtil;
import com.cc.common.Utils.RRException;
import com.cc.common.Utils.RedisUtil;
import com.cc.common.Utils.TokenSignUtil;
import com.cc.common.annotation.IgnoreToken;
import com.cc.model.user.TokenModel;
import com.cc.service.user.TokenService;
import com.github.pagehelper.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MyAdapterInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenService tokenService;
    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    Logger logger = LoggerFactory.getLogger(MyAdapterInterceptor.class);

    public MyAdapterInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        MyUtil.setHeadByResponse(response);
        if ("OPTIONS".equals(method)) {
            return true;
        } else {
            System.out.println("getQueryString  ..." + request.getQueryString());
            this.logger.info(request.getRequestURI() + "......start");
            String queryStr = request.getQueryString();
            String token;
            if (StringUtil.isNotEmpty(queryStr)) {
                if (hasDanger(queryStr)) {
                    request.setAttribute("errorMsgInfo", "非法请求!!");
                    response.setStatus(403);
                    return false;
                }

                token = URLDecoder.decode(queryStr);
                if (token != null && hasDanger(token)) {
                    request.setAttribute("errorMsgInfo", "非法请求!!");
                    response.setStatus(403);
                    return false;
                }
            }

            if (handler instanceof HandlerMethod) {
                Annotation annotation = ((HandlerMethod)handler).getMethodAnnotation(IgnoreToken.class);
                if (annotation != null) {
                    return true;
                } else {
                    token = request.getHeader("token");
                    if (StringUtils.isBlank(token)) {
                        token = request.getParameter("token");
                    }

                    if (StringUtils.isBlank(token)) {
                        throw new RRException("token不能为空");
                    } else {
                        TokenModel tokenModel = this.tokenService.queryByToken(token);
                        if (tokenModel != null && tokenModel.getExpireTime().getTime() >= System.currentTimeMillis()) {
                            request.setAttribute(LOGIN_USER_KEY, tokenModel.getUserId());
                            return true;
                        } else {
                            throw new RRException("token失效，请重新登录");
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.logger.info(request.getRequestURI() + "......end");
    }

    private static boolean hasDanger(String queryStr) {
        boolean has = false;
        String[] dangerChar = new String[]{";", "'", "\"", "<>", "<", ">", "()", "(", ")"};
        String[] var3 = dangerChar;
        int var4 = dangerChar.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String danger = var3[var5];
            if (queryStr.contains(danger)) {
                has = true;
                break;
            }
        }

        return has;
    }

    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(403);
        String token = request.getHeader("Authorization");
        String timestamp = request.getHeader("Timestamp");
        String signature = request.getHeader("Signature");
        if (!StringUtil.isEmpty(token) && !StringUtil.isEmpty(signature) && !StringUtil.isEmpty(timestamp)) {
            if (NumberUtils.isDigits(timestamp) && System.currentTimeMillis() - NumberUtils.toLong(timestamp) <= 120000L) {
                if (TokenSignUtil.isAvailableToken(this.redisUtil, token)) {
                    request.setAttribute("errorMsgInfo", "token 错误!!");
                    return false;
                } else {
                    Enumeration<String> pNames = request.getParameterNames();
                    Map<String, String> params = new HashMap();
                    params.put("Authorization", token);
                    params.put("Timestamp", timestamp);

                    String mySign;
                    while(pNames.hasMoreElements()) {
                        mySign = (String)pNames.nextElement();
                        params.put(mySign, request.getParameter(mySign));
                    }

                    try {
                        mySign = TokenSignUtil.getSign(params, false);
                        if (!signature.equals(mySign.trim())) {
                            request.setAttribute("errorMsgInfo", "签名 错误!!");
                            return false;
                        } else {
                            return true;
                        }
                    } catch (UnsupportedEncodingException var9) {
                        var9.printStackTrace();
                        request.setAttribute("errorMsgInfo", "签名 错误!!");
                        return false;
                    }
                }
            } else {
                request.setAttribute("errorMsgInfo", "时间戳错误 或者超时!!");
                return false;
            }
        } else {
            request.setAttribute("errorMsgInfo", "Authorization,Timestamp,Signature 参数必须传!!");
            return false;
        }
    }
}
