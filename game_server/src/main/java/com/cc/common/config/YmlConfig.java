//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ymlmanager")
public class YmlConfig {
    private Map<String, Object> mapProps = new HashMap();

    public YmlConfig() {
    }

    public Map<String, Object> getMapProps() {
        return this.mapProps;
    }

    public void setMapProps(Map<String, Object> mapProps) {
        this.mapProps = mapProps;
    }
}
