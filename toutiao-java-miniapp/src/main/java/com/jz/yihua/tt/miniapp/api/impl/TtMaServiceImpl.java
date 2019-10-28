package com.jz.yihua.tt.miniapp.api.impl;

import com.google.common.base.Joiner;
import com.jz.yihua.toutiao.common.bean.TtAccessToken;
import com.jz.yihua.toutiao.common.error.TtError;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.DataUtils;
import com.jz.yihua.toutiao.common.util.http.*;
import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;
import com.jz.yihua.toutiao.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.jz.yihua.tt.miniapp.api.TtMaService;
import com.jz.yihua.tt.miniapp.bean.TtMaJscode2SessionResult;
import com.jz.yihua.tt.miniapp.config.TtMaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: joey huang
 * @Date: 2019/10/22 12:28
 */
@Slf4j
public class TtMaServiceImpl implements TtMaService, IHttpRequest<CloseableHttpClient> {

    private CloseableHttpClient httpClient;
    private TtMaConfig ttMaConfig;

    private int retrySleepMillis = 1000;
    /**
     * 最大重试次数
     */
    private int maxRetryTimes = 5;

    @Override
    public void setTtMaConfig(TtMaConfig ttConfigProvider) {
        this.ttMaConfig = ttConfigProvider;
        this.initHttp();
    }

    public TtMaConfig getTtMaConfig() {
        return this.ttMaConfig;
    }

    @Override
    public void initHttp() {
        TtMaConfig configStorage = this.getTtMaConfig();
        ApacheHttpClientBuilder apacheHttpClientBuilder = configStorage.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }
        this.httpClient = apacheHttpClientBuilder.build();
    }



    @Override
    public TtMaJscode2SessionResult jsCode2SessionInfo(String jsCode) throws TtErrorException {
        final TtMaConfig config = getTtMaConfig();
        Map<String, String> params = new HashMap<>(8);
        params.put("appid", config.getAppid());
        params.put("secret", config.getSecret());
        params.put("code", jsCode);
        String result = get(JSCODE_TO_SESSION_URL, Joiner.on("&").withKeyValueSeparator("=").join(params));
        return TtMaJscode2SessionResult.fromJson(result);
    }

    @Override
    public String get(String url, String queryParam) throws TtErrorException {
        return execute(AbstractGetRequestExecutor.create(this), url, queryParam);
    }


    @Override
    public String post(String url, String postData) throws TtErrorException {
        return execute(AbstractPostRequestExecutor.create(this), url, postData);
    }

    @Override
    public <R, P> R execute(IHttpRequestExecutor<R, P> executor, String uri, P param) throws TtErrorException {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, param);
            } catch (TtErrorException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", maxRetryTimes);
                    //最后一次重试失败后，直接抛出异常，不再等待
                    throw new RuntimeException("头条服务端异常，超出重试次数");
                }

                TtError error = e.getError();
                // -1 系统繁忙, 1000ms后重试
                if (error.getErrorCode() == -1) {
                    int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                    try {
                        log.warn("头条系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new RuntimeException("头条服务端异常，超出重试次数");
    }

    private <R, P> R executeInternal(IHttpRequestExecutor<R, P> executor, String uri, P param) throws TtErrorException {
        P dataForLog = DataUtils.handleDataWithSecret(param);

        try {
            R result = executor.execute(uri, param);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, dataForLog, result);
            return result;
        } catch (TtErrorException e) {
            TtError error = e.getError();
            if (error.getErrorCode() != 0) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, dataForLog, error);
                throw new TtErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, dataForLog, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public String getAccessToken() throws TtErrorException {

        String url = String.format(TtMaService.GET_ACCESS_TOKEN_URL, this.getTtMaConfig().getAppid(),
                this.getTtMaConfig().getSecret());
        try {
            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = getRequestHttpClient().execute(httpGet)) {
                String resultContent = new BasicResponseHandler().handleResponse(response);
                TtError error = TtError.fromJson(resultContent);
                if (error.getErrorCode() != 0) {
                    throw new TtErrorException(error);
                }
                TtAccessToken accessToken = TtAccessToken.fromJson(resultContent);

                return accessToken.getAccessToken();
            } finally {
                httpGet.releaseConnection();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
