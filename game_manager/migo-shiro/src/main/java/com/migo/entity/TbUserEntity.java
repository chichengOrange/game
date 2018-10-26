package com.migo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class TbUserEntity implements Serializable {
    private static final long serialVersionUID = -167607904051963460L;
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private transient String password;

    @NotBlank(message = "邮箱不能为空")
    private String mobile;

    private String email;

    private Integer status;

    private Date createTime;
}