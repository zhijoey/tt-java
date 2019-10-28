package com.jz.yihua.ttpay.config.impl;

import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;
import com.jz.yihua.ttpay.config.TtPayConfig;

/**
 * 基于内存的配置provider，在实际生产环境中应该将这些配置持久化
 *
 * @author joey huang
 */

public class TtPayDefaultConfigImpl implements TtPayConfig {

  private volatile String appid;
  private volatile String merchantid;
  private volatile String secret;
  private volatile String tpDomainUrl;
  private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

  @Override
  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  @Override
  public String getMerchantid() {
    return merchantid;
  }

  public void setMerchantid(String merchantid) {
    this.merchantid = merchantid;
  }

  @Override
  public String getSecret() {
    return secret;
  }

  @Override
  public String getTpDomainUrl() {
    return tpDomainUrl;
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
