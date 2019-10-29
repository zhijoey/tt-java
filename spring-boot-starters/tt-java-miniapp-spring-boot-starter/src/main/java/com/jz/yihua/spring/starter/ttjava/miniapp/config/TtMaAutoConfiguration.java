package com.jz.yihua.spring.starter.ttjava.miniapp.config;


import com.jz.yihua.spring.starter.ttjava.miniapp.properties.TtMaProperties;
import com.jz.yihua.tt.miniapp.api.TtMaService;
import com.jz.yihua.tt.miniapp.api.impl.TtMaServiceImpl;
import com.jz.yihua.tt.miniapp.config.impl.TtMaDefaultConfigImpl;
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
@ConditionalOnClass(TtMaService.class)
@EnableConfigurationProperties(TtMaProperties.class)
@ConditionalOnProperty(prefix = "tt.miniapp", value = "enabled", matchIfMissing = true)
public class TtMaAutoConfiguration {
  private TtMaProperties properties;

  /**
   * 小程序service.
   *
   * @return 小程序service
   */
  @Bean
  @ConditionalOnMissingBean(TtMaService.class)
  public TtMaService ttMaService() {
    TtMaDefaultConfigImpl config = new TtMaDefaultConfigImpl();
    config.setAppid(StringUtils.trimToNull(this.properties.getAppid()));
    config.setSecret(StringUtils.trimToNull(this.properties.getSecret()));
    final TtMaServiceImpl service = new TtMaServiceImpl() ;
    service.setTtMaConfig(config);
    return service;
  }
}
