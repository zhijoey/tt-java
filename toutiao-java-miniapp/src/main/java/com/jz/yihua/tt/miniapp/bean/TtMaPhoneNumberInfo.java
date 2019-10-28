package com.jz.yihua.tt.miniapp.bean;

import com.jz.yihua.tt.miniapp.util.json.TtMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 头条用户授权的手机号相关信息
 * @author joey huang
 */
@Data
public class TtMaPhoneNumberInfo implements Serializable {
  private static final long serialVersionUID = 5944235686356338368L;
  private String phoneNumber;
  private String purePhoneNumber;
  private String countryCode;
  private Watermark watermark;

  public static TtMaPhoneNumberInfo fromJson(String json) {
    return TtMaGsonBuilder.create().fromJson(json, TtMaPhoneNumberInfo.class);
  }

  @Data
  public static class Watermark {
    private String timestamp;
    private String appid;
  }
}
