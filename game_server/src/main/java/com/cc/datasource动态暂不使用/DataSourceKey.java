package com.cc.datasource动态暂不使用;

/**
 * @author RocLiu [apedad@qq.com]
 * @version 1.0
 */
public enum DataSourceKey {

    DB_MASTER("DB_MASTER"),

    DB_SLAVE1("DB_SLAVE1");

    private String key;


    DataSourceKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
