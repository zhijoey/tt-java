package com.jz.yihua.tt.miniapp.bean;

import com.google.gson.annotations.SerializedName;
import com.jz.yihua.tt.miniapp.util.json.TtMaGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <pre>
 * code换取session_key接口的响应
 * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html#wxloginobject
 * 微信返回报文：{"session_key":"nzoqhc3OnwHzeTxJs+inbQ==","openid":"oVBkZ0aYgDMDIywRdgPW8-joxXc4"}
 * </pre>
 * @author joey huang
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtMaJscode2SessionResult implements Serializable {
  private static final long serialVersionUID = 1428117290869155406L;

  @SerializedName("session_key")
  private String sessionKey;

  @SerializedName("openid")
  private String openid;

  public static TtMaJscode2SessionResult fromJson(String json) {
    return TtMaGsonBuilder.create().fromJson(json, TtMaJscode2SessionResult.class);
  }

}
