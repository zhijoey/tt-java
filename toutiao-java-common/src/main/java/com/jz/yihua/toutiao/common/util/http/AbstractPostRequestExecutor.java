package com.jz.yihua.toutiao.common.util.http;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.apache.PostRequestExecutor;

import java.io.IOException;

/**
 * POST请求抽象类
 *
 * @author joey huang
 */
public abstract class AbstractPostRequestExecutor<T> implements IHttpRequestExecutor<String, String> {
  protected IHttpRequest<T> httpRequest;

  public AbstractPostRequestExecutor(IHttpRequest httpRequest) {
    this.httpRequest = httpRequest;
  }

  @Override
  public void execute(String uri, String data, ResponseHandler<String> handler) throws TtErrorException, IOException {
    handler.handle(this.execute(uri, data));
  }

  public static IHttpRequestExecutor<String, String> create(IHttpRequest httpRequest) {
    return new PostRequestExecutor(httpRequest);
  }

}
