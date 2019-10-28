package com.jz.yihua.toutiao.common.util.http.apache;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * httpclient build interface.
 * 大部分代码拷贝自Wx-java中ApacheHttpClientBuilder
 *
 * @author joey huang
 */
public interface ApacheHttpClientBuilder {

  /**
   * 构建httpclient实例.
   *
   * @return new instance of CloseableHttpClient
   */
  CloseableHttpClient build();

  /**
   * ssl连接socket工厂.
   */
  ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory);
}
