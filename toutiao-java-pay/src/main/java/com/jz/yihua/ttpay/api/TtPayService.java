package com.jz.yihua.ttpay.api;

import com.jz.yihua.toutiao.common.error.InvalidPayRequestException;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.IHttpRequestExecutor;
import com.jz.yihua.ttpay.bean.TtTradeCreateRequest;
import com.jz.yihua.ttpay.bean.TtTradeCreateResponse;
import com.jz.yihua.ttpay.config.TtPayConfig;

/**
 * <pre>
 *   头条支付相关接口
 * </pre>
 * @Author: joey huang
 * @Date: 2019/10/25 14:18
 */
public interface TtPayService {

    /**
     * 初始化http请求对象.
     */
    void initHttp();
    /**
     * 注入 {@link TtPayConfig} 的实现.
     */
    void setTtPayConfig(TtPayConfig ttPayConfigProvider);

    TtTradeCreateResponse genPayRequestParams(TtTradeCreateRequest request) throws InvalidPayRequestException, TtErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求.
     */
    String get(String url, String queryParam) throws TtErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求.
     */
    String post(String url, String postData) throws TtErrorException;

    /**
     * <pre>
     * 可自己根据需要来构造IHttpRequestExecutor用来处理不同的参数和不同的返回类型。
     * </pre>
     */
    <R, P> R execute(IHttpRequestExecutor<R, P> executor, String uri, P param) throws TtErrorException;



}
