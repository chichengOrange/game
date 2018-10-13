//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class TokenModel implements Serializable {
    private static final long serialVersionUID = 181802921470594218L;
    private Long userId;
    private String token;
    private Date expireTime;
    private Date updateTime;
}
