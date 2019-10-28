package com.jz.yihua.toutiao.common.util.http.apache;

import com.jz.yihua.toutiao.common.error.TtError;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.IHttpRequest;
import com.jz.yihua.toutiao.common.util.http.AbstractGetRequestExecutor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @author joey huang
 */
public class GetRequestExecutor extends AbstractGetRequestExecutor<CloseableHttpClient> {

  public GetRequestExecutor(IHttpRequest iHttpRequest) {
    super(iHttpRequest);
  }

  private static final String URI_PARAM_QUERY_SYMBOL = "?";

  @Override
  public String execute(String uri, String queryParam) throws TtErrorException, IOException {
    if (queryParam != null) {
      //拼接请求参数
      if (!uri.contains(URI_PARAM_QUERY_SYMBOL)) {
        uri += URI_PARAM_QUERY_SYMBOL;
      }
      uri += uri.endsWith(URI_PARAM_QUERY_SYMBOL) ? queryParam : '&' + queryParam;
    }
    HttpGet httpGet = new HttpGet(uri);

    try (CloseableHttpResponse response = iHttpRequest.getRequestHttpClient().execute(httpGet)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      TtError error = TtError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new TtErrorException(error);
      }
      return responseContent;
    } finally {
      httpGet.releaseConnection();
    }
  }

}
