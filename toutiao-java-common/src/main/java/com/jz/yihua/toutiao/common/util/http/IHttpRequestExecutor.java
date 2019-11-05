package com.jz.yihua.toutiao.common.util.http;

import com.jz.yihua.toutiao.common.error.TtErrorException;

import java.io.IOException;

/**
 * http请求执行器.
 *
 * @param <R> 返回值类型(response - R)
 * @param <P> 请求参数类型(param - P)
 * @author huangzhi
 */
public interface IHttpRequestExecutor<R, P> {

  /**
   * 执行http请求.
   *
   * @param uri  uri
   * @param data 请求数据
   * @return 响应结果
   * @throws TtErrorException 自定义异常
   * @throws IOException      io异常
   */
  R execute(String uri, P data) throws TtErrorException, IOException;


  /**
   * 执行http请求.
   *
   * @param uri      uri
   * @param data     请求数据
   * @param handler http response 处理器
   * @throws TtErrorException 自定义异常
   * @throws IOException      io异常
   */
  void execute(String uri, P data, ResponseHandler<R> handler) throws TtErrorException, IOException;
}
