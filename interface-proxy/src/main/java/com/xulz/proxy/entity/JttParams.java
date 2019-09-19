package com.xulz.proxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String token;
    private Integer pageNo;
    private Integer pageSize;
}
