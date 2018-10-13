package com.cc.common.Utils;

import com.cc.common.config.YmlConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by CCK on 2018/4/9.
 */
public class YmlUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static Map<String, Object> propertiesMap = null;

    public YmlUtil() {
    }

    public static String getConfigByKey(String key) {
        if (propertiesMap == null) {
            YmlConfig ymlConfig = applicationContext.getBean(YmlConfig.class);
            propertiesMap = ymlConfig.getMapProps();
        }
        return propertiesMap.get(key) != null ? propertiesMap.get(key).toString() : "";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (YmlUtil.applicationContext == null) {
            YmlUtil.applicationContext = applicationContext;
        }
    }


}
