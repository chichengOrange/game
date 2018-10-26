package com.cc.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1871022838701180L;
    @ApiModelProperty(hidden = true)
    private Long userId;
    private String username;
    private String mobile;
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient String password;
    @ApiModelProperty(
            hidden = true
    )
    private Date createTime;
    @ApiModelProperty(
            hidden = true
    )
    private Integer status;
    private String qrCode;
    private String studentId;


    private String identity;

    private String realname;

    private Integer rnameStatus;

}
