package com.jz.yihua.toutiao.common.bean;

import com.google.gson.annotations.SerializedName;
import com.jz.yihua.toutiao.common.util.json.TtGsonBuilder;
import lombok.Data;


import java.io.Serializable;

/**
 * access token.
 *
 * @author joey huang
 */
@Data
public class TtAccessToken implements Serializable {

  private static final long serialVersionUID = -4091458038798050047L;

  @SerializedName("access_token")
  private String accessToken;

  @SerializedName("expires_in")
  private int expiresIn = -1;

  public static TtAccessToken fromJson(String json) {
    return TtGsonBuilder.create().fromJson(json, TtAccessToken.class);
  }

}
