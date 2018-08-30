package com.inner.api.base.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author v-kecongcong
 * @date 2018/8/7
 */
@Data
public class User implements Serializable{

    private Integer userId;

    private String userName;

    private String password;

    @NotNull(message = "电话号码不能为空")
    private String phone;
}
