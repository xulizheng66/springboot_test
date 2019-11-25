package com.xulz.proxy.jtt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: TODO
 * @author: xulz
 * @date： 2019/10/23 16:19
 * @version: 1.0
 */
@Data
public class Params implements Serializable {

    /**
     * 服务唯一有效凭证
     */
    @NotBlank(message = "token不能为空")
    @JsonProperty("jttToken")
    private String token;
    @NotNull(message = "pageNo不能为空")
    private Integer pageNo;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
    // 时间
    private String timekey;

}
