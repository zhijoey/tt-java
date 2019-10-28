package com.jz.yihua.toutiao.common.error;

import com.jz.yihua.toutiao.common.TtType;
import com.jz.yihua.toutiao.common.util.json.TtGsonBuilder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 头条错误码.
 *
 * @author joey huang
 */
@Data
@Builder
public class TtError implements Serializable {

  private static final long serialVersionUID = -9084395842773812704L;

  private int errorCode;

  private String errorMsg;

  private String errorMsgEn;

  private String json;

  public static TtError fromJson(String json) {
    return fromJson(json, null);
  }

  public static TtError fromJson(String json, TtType type) {
    final TtError ttError = TtGsonBuilder.create().fromJson(json, TtError.class);
    if (StringUtils.isNotEmpty(ttError.getErrorMsg())) {
      ttError.setErrorMsgEn(ttError.getErrorMsg());
    }

    if (type == null) {
      return ttError;
    }

    if (type == TtType.MiniApp) {
        final String msg = TtMaErrorMsgEnum.findMsgByCode(ttError.getErrorCode());
        if (msg != null) {
          ttError.setErrorMsg(msg);
        }
    }

    return ttError;
  }

  @Override
  public String toString() {
    if (this.json != null) {
      return this.json;
    }
    return "错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
  }

}
