package com.jz.yihua.spring.starter.ttjava.miniapp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性配置类.
 *
 * @author joey huang
 * @date 2019-08-10
 */
@Data
@ConfigurationProperties(prefix = "tt-config.ma")
public class TtMaProperties {
  /**
   * 设置头条小程序的appid.
   */
  private String appid;

  /**
   * 设置头条小程序的Secret.
   */
  private String secret;


}
