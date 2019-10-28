package com.jz.yihua.spring.starter.ttjava.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性配置类.
 *
 * @author joey huang
 * @date 2019-08-10
 */
@Data
@ConfigurationProperties(prefix = "tt-config.pay")
public class TtPayProperties {
  /**
   * 设置头条小程序支付的appid.
   */
  private String appid;

  /**
   * 头条小程序商户号
   */
  private String merchantid;

  /**
   * 设置头条小程序支付的Secret.
   */
  private String secret;


}
