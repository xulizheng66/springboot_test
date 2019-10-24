package com.xulz.proxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description: TODO
 * @Author xulz
 * @Date 2019/8/5 17:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JttParams {
    // 接口请求地址  http://59.219.195.5:6080/DATACENTERPro/api
    private String restClienturl;
    // 接口请求地址  /dataShare/getZWResource
    private String requestPath;
    // 格式:yyymmdd
    private String timekey;
    // 服务唯一有效凭证
    @NotBlank(message = "token不能为空")
    private String token;
    @NotNull(message = "pageNo不能为空")
    private Integer pageNo;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
}
