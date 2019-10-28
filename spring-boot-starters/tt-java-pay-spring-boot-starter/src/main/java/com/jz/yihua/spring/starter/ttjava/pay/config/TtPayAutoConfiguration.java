package com.jz.yihua.spring.starter.ttjava.pay.config;

import com.jz.yihua.spring.starter.ttjava.pay.properties.TtPayProperties;
import com.jz.yihua.ttpay.api.TtPayService;
import com.jz.yihua.ttpay.api.impl.TtPayServiceImpl;
import com.jz.yihua.ttpay.config.impl.TtPayDefaultConfigImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置.
 * @author joey huang
 * @date 2019-08-10
 */
@AllArgsConstructor
@Configuration
@ConditionalOnClass(TtPayService.class)
@EnableConfigurationProperties(TtPayProperties.class)
@ConditionalOnProperty(prefix = "tt.pay", value = "enabled", matchIfMissing = true)
public class TtPayAutoConfiguration {
  private TtPayProperties properties;

  /**
   * 小程序service.
   *
   * @return 小程序service
   */
  @Bean
  @ConditionalOnMissingBean(TtPayService.class)
  public TtPayService service() {
    TtPayDefaultConfigImpl config = new TtPayDefaultConfigImpl();
    config.setAppid(StringUtils.trimToNull(this.properties.getAppid()));
    config.setMerchantid(StringUtils.trimToNull(this.properties.getMerchantid()));
    config.setSecret(StringUtils.trimToNull(this.properties.getSecret()));
    config.setTpDomainUrl(StringUtils.trimToNull(this.properties.getTpDomainUrl()));
    final TtPayServiceImpl service = new TtPayServiceImpl() ;
    service.setTtMaConfig(config);
    return service;
  }
}
