package com.jz.yihua.toutiao.common.util.http;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.apache.GetRequestExecutor;

import java.io.IOException;

/**
 * GET请求执行器.
 *
 * @author joey huang
 */
public abstract class AbstractGetRequestExecutor<T> implements IHttpRequestExecutor<String, String> {
  protected IHttpRequest<T> iHttpRequest;

  public AbstractGetRequestExecutor(IHttpRequest<T> iHttpRequest) {
    this.iHttpRequest = iHttpRequest;
  }

  @Override
  public void execute(String uri, String data, ResponseHandler<String> handler) throws TtErrorException, IOException {
    handler.handle(this.execute(uri, data));
  }

  public static IHttpRequestExecutor<String, String> create(IHttpRequest iHttpRequest) {

    return new GetRequestExecutor(iHttpRequest);

  }

}
