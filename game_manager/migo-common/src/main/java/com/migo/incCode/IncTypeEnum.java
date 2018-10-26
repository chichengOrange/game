package com.migo.incCode;


/**
 *
 * <p>Title: BizTypeEnum</p>
 * <p>Description: 编码生成枚举类</p>
 * @author cck
 * @date 2018年10月24日  下午5:16:13
 */

public enum IncTypeEnum {

    TB_USER_ID("TB_USER_ID", "用户ID", 10),

    GAME_ID("GAME_ID", "游戏ID", 12),

    REPLAY_ID("REPLAY_ID", "replay ID", 16);

    private String key;

    private String name;

    private Integer  prefix;

    IncTypeEnum(String key, String name, Integer prefix) {
        this.key = key;
        this.name = name;
        this.prefix = prefix;
    }

    /**
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取枚举消息
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    public Integer getPrefix() {
        return prefix;
    }
}
