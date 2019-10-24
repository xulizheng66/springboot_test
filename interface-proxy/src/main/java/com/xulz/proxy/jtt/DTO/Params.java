package com.xulz.proxy.jtt.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: TODO
 * @author: xulz
 * @date： 2019/10/23 16:19
 * @version: 1.0
 */
@Data
public class Params {

    /**
     * 服务唯一有效凭证
     */
    @NotBlank(message = "token不能为空")
    private String token;
    @NotNull(message = "pageNo不能为空")
    private Integer pageNo;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
    // 时间
    private String timekey;

}
