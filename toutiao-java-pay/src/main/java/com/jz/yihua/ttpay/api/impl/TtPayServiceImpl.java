package com.jz.yihua.ttpay.api.impl;

import com.jz.yihua.toutiao.common.error.InvalidPayRequestException;
import com.jz.yihua.toutiao.common.error.TtError;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.DataUtils;
import com.jz.yihua.toutiao.common.util.http.AbstractGetRequestExecutor;
import com.jz.yihua.toutiao.common.util.http.AbstractPostRequestExecutor;
import com.jz.yihua.toutiao.common.util.http.IHttpRequest;
import com.jz.yihua.toutiao.common.util.http.IHttpRequestExecutor;
import com.jz.yihua.toutiao.common.util.http.apache.ApacheHttpClientBuilder;
import com.jz.yihua.toutiao.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.jz.yihua.ttpay.api.TtPayService;
import com.jz.yihua.ttpay.bean.*;
import com.jz.yihua.ttpay.config.TtPayConfig;
import com.jz.yihua.ttpay.constant.TtPayConstants;
import com.jz.yihua.ttpay.util.http.PayPostRequestExecutor;
import com.jz.yihua.ttpay.util.json.TtPayGsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Map;


/**
 * @Author: joey huang
 * @Date: 2019/10/23 15:43
 */
@Slf4j
public class TtPayServiceImpl implements TtPayService, IHttpRequest<CloseableHttpClient> {

    private TtPayConfig ttPayConfig;
    private CloseableHttpClient httpClient;

    private int retrySleepMillis = 1000;
    /**
     * 最大重试次数
     */
    private int maxRetryTimes = 5;

    private static String TT_PAY_APPLET_VERSION1_API_URL = "https://tp-pay-test.snssdk.com";

    @Override
    public void setTtPayConfig(TtPayConfig ttPayConfigProvider) {
        this.ttPayConfig = ttPayConfigProvider;
    }

    public TtPayConfig getTtMaConfig() {
        return this.ttPayConfig;
    }

    @Override
    public TtTradeCreateResponse genPayRequestParams(TtTradeCreateRequest request) throws InvalidPayRequestException, TtErrorException {

        TtTradeCreateResponse response = new TtTradeCreateResponse();

        String versionBackend = request.getVersion();
        String versionApplet = request.getAppletVersion();

        request.valid(ttPayConfig.getAppid(), ttPayConfig.getMerchantid());

        if ( versionBackend.equals(TtPayConstants.PAY_DEFAULT_VERSION) || versionApplet.equals(TtPayAppletVersionEnum.Applet_Version_2p.getCode())
                || versionApplet.equals(TtPayAppletVersionEnum.Applet_Version_1.getCode()) ) {
            response = getTradeNo4AppletVersion1(request);
            request.setTradeNo(response.getTradeNo());
        }

        response.genCashDeskAppletParams(request, ttPayConfig.getAppid(), ttPayConfig.getMerchantid(), ttPayConfig.getSecret());
        return response;
    }

    private TtTradeCreateResponse getTradeNo4AppletVersion1(TtTradeCreateRequest request) throws TtErrorException {
        Map<String, Object> signParams = request.encode(ttPayConfig.getMerchantid(), ttPayConfig.getAppid(), ttPayConfig.getSecret());
        StringBuilder urlValues = new StringBuilder();

        for (String key: signParams.keySet()) {
            Object val = signParams.get(key);
            if (urlValues.length() == 0) {
                urlValues.append(key);
                urlValues.append("=");
                urlValues.append(val);
            } else {
                urlValues.append("&");
                urlValues.append(key);
                urlValues.append("=");
                urlValues.append(val);
            }
        }
        String tpDomainUrl =  TT_PAY_APPLET_VERSION1_API_URL + "/" + request.getPath();
        return callAppletVersion1Api(tpDomainUrl, urlValues.toString(), request);
    }

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public void initHttp() {
        TtPayConfig configStorage = this.getTtMaConfig();
        ApacheHttpClientBuilder apacheHttpClientBuilder = configStorage.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }
        this.httpClient = apacheHttpClientBuilder.build();

    }

    @Override
    public String get(String url, String queryParam) throws TtErrorException {
        return execute(AbstractGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws TtErrorException {
        return execute(AbstractPostRequestExecutor.create(this), url, postData);
    }

    private <T extends BaseTtPayResponse> T callAppletVersion1Api(String url, String postData, BaseTtPayRequest ttPayRequest) throws TtErrorException {

        String responseStr = execute(PayPostRequestExecutor.create(this), url, postData);
        BaseResponse<T> baseResponse = TtPayGsonBuilder.create().fromJson(responseStr, ttPayRequest.getResponseType());

        T ttPayResponse = baseResponse.getResponse();
        if(!ttPayResponse.isSuccess()) {
            throw new TtErrorException(TtError.builder().errorCode(Integer.parseInt(ttPayResponse.getCode())).errorMsg(ttPayResponse.getMsg()).build());
        }
        return ttPayResponse;
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
}
