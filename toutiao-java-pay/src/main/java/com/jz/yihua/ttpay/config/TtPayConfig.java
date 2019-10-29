package com.jz.yihua.ttpay.config;

import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;

/**
 * @Author: joey huang
 * @Date: 2019/10/23 15:20
 */
public interface TtPayConfig {

    String getAppid();

    String getMerchantid();

    String getSecret();

    /**
     * http client builder
     *
     * @return ApacheHttpClientBuilder
     */
    ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
