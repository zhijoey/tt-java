package com.jz.yihua.tt.miniapp.api;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.toutiao.common.util.http.IHttpRequestExecutor;
import com.jz.yihua.tt.miniapp.bean.TtMaJscode2SessionResult;
import com.jz.yihua.tt.miniapp.config.TtMaConfig;

/**
 * @author joey huang
 */
public interface TtMaService {

    /**
     * 获取access_token.
     */
    String GET_ACCESS_TOKEN_URL = "https://developer.toutiao.com/api/apps/token?grant_type=client_credential&appid=%s&secret=%s";

    String JSCODE_TO_SESSION_URL = "https://developer.toutiao.com/api/apps/jscode2session";

  /**
   * 注入 {@link TtMaConfig} 的实现.
   */
  void setTtMaConfig(TtMaConfig ttConfigProvider);


  /**
   * 初始化http请求对象.
   */
  void initHttp();

    /**
     * 获取登录后的session信息.
     *
     * @param jsCode 登录时获取的 code
     */
    TtMaJscode2SessionResult jsCode2SessionInfo(String jsCode) throws TtErrorException;


    /**
     * <pre>
     * 注意：该方法获取access_token, 为强制刷新access_token，在调用方维护获取到的accessToken 更新以及过期策略。
     * access_token 的有效期为 2 个小时，需要定时刷新 access_token，重复获取会导致之前一次获取的 access_token 的有效期缩短为 5 分钟。
     * 该方法非线程安全，需在调用时加锁控制。
     * </pre>
     */
    String getAccessToken() throws TtErrorException;

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
