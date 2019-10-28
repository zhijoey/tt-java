package com.jz.yihua.ttpay.util.http;


import com.jz.yihua.toutiao.common.error.TtError;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.AbstractPostRequestExecutor;
import com.jz.yihua.toutiao.common.util.http.IHttpRequest;
import com.jz.yihua.toutiao.common.util.http.apache.Utf8ResponseHandler;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 *
 * @author huanzhi
 * @date 2019/10/24
 */
public class PayPostRequestExecutor extends AbstractPostRequestExecutor<CloseableHttpClient> {

  public PayPostRequestExecutor(IHttpRequest iHttpRequest) {
    super(iHttpRequest);
  }

  @Override
  public String execute(String uri, String postEntity) throws TtErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);

    if (postEntity != null) {
      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
      entity.setContentType("application/x-www-form-urlencoded");
      httpPost.setEntity(entity);
    }

    try (CloseableHttpResponse response = httpRequest.getRequestHttpClient().execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      if (responseContent.isEmpty()) {
        throw new TtErrorException(TtError.builder().errorCode(9999).errorMsg("无response返回").build());
      }

      TtError error = TtError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        //反序列化后为异常返回
        throw new TtErrorException(error);
      }
      return responseContent;
    } finally {
      //释放连接
      httpPost.releaseConnection();
    }
  }

}
