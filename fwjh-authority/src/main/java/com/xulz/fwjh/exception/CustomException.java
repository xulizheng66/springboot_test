package com.xulz.fwjh.exception;

import lombok.Data;
import org.springframework.web.client.RestClientException;

/**
 * @Description: 自定义异常捕获类
 * @Author xulz
 * @Date 2019/7/2 14:35
 */
@Data
public class CustomException extends RestClientException {

    private RestClientException restClientException;
    private String body;

    public CustomException(String msg, RestClientException restClientException, String body) {
        super(msg);
        this.restClientException = restClientException;
        this.body = body;
    }

}

