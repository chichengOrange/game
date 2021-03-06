//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.runner;

import com.migo.entity.SysConfigEntity;
import com.migo.service.SysConfigService;
import com.migo.utils.ConfigUtil;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner {
    @Autowired
    private SysConfigService sysConfigService;

    public MyApplicationRunner() {
    }

    @PostConstruct
    public void addConfig() {
        List<SysConfigEntity> list = this.sysConfigService.queryList((Map)null);
        System.out.println(list);
        list.forEach((item) -> {
            ConfigUtil.addSysParamCache(item.getKey(), item.getValue());
        });
    }
}
