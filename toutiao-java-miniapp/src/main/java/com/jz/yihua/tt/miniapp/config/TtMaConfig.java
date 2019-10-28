package com.jz.yihua.tt.miniapp.config;


import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;

/**
 * 小程序配置
 *
 * @author joey huang
 */
public interface TtMaConfig {

  String getAppid();

  String getSecret();
  /**
   * http client builder
   *
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();

}
