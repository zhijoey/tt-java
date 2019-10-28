package com.jz.yihua.tt.miniapp.config.impl;

import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;
import com.jz.yihua.tt.miniapp.config.TtMaConfig;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 *
 * @author joey huang
 */

public class TtMaDefaultConfigImpl implements TtMaConfig {

  protected volatile String appid;
  private volatile String secret;
  private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

  @Override
  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  @Override
  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret){
    this.secret = secret;
  }

  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return this.apacheHttpClientBuilder;
  }

  public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
    this.apacheHttpClientBuilder = apacheHttpClientBuilder;
  }
}
