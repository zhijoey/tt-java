package com.jz.yihua.tt.miniapp.constant;

/**
 * <pre>
 *  小程序常量.
 * </pre>
 *
 * @author joey huang
 */
public class TtMaConstants {
  /**
   * 头条接口返回的参数errcode.
   */
  public static final String ERRCODE = "errcode";

  /**
   * 素材类型.
   */
  public static class MediaType {
    /**
     * 图片.
     */
    public static final String IMAGE = "image";
  }

  /**
   * 消息格式.
   */
  public static class MsgDataFormat {
    public static final String XML = "XML";
    public static final String JSON = "JSON";
  }

  public static final class ErrorCode {
    /**
     * 40001 获取access_token时AppSecret错误，或者access_token无效.
     */
    public static final int ERR_40001 = 40001;

    /**
     * 42001 access_token超时.
     */
    public static final int ERR_42001 = 42001;

    /**
     * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期）.
     */
    public static final int ERR_40014 = 40014;
  }
}
