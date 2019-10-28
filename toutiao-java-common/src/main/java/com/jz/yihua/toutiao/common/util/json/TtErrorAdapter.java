package com.jz.yihua.toutiao.common.util.json;

import com.google.gson.*;
import com.jz.yihua.toutiao.common.error.TtError;

import java.lang.reflect.Type;

/**
 * 头条接口返回错误json转换adapter
 * @author joey huang
 */
public class TtErrorAdapter implements JsonDeserializer<TtError> {

  @Override
  public TtError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    TtError.TtErrorBuilder errorBuilder = TtError.builder();
    JsonObject ttErrorJsonObject = json.getAsJsonObject();

    if (ttErrorJsonObject.get("errcode") != null && !ttErrorJsonObject.get("errcode").isJsonNull()) {
      errorBuilder.errorCode(GsonHelper.getAsPrimitiveInt(ttErrorJsonObject.get("errcode")));
    }
    if (ttErrorJsonObject.get("errmsg") != null && !ttErrorJsonObject.get("errmsg").isJsonNull()) {
      errorBuilder.errorMsg(GsonHelper.getAsString(ttErrorJsonObject.get("errmsg")));
    }

    errorBuilder.json(json.toString());

    return errorBuilder.build();
  }

}
