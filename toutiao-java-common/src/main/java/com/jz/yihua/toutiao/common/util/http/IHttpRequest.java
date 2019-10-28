package com.jz.yihua.toutiao.common.util.http;

/**
 * @author joey huang
 */
public interface IHttpRequest<T> {

  /**
   * 返回httpClient.
   *
   * @return 返回httpClient
   */
  T getRequestHttpClient();


}
