package com.jz.yihua.tt.miniapp.bean;

import com.jz.yihua.tt.miniapp.util.json.TtMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author joey huang
 */
@Data
public class TtMaUserInfo implements Serializable {
  private static final long serialVersionUID = 6719822331555402137L;

  private String openId;
  private String nickName;
  private String gender;
  private String language;
  private String city;
  private String province;
  private String country;
  private String avatarUrl;
  private String unionId;
  private Watermark watermark;

  public static TtMaUserInfo fromJson(String json) {
    return TtMaGsonBuilder.create().fromJson(json, TtMaUserInfo.class);
  }

  @Data
  public static class Watermark {
    private String timestamp;
    private String appid;
  }
}
