package com.jz.yihua.toutiao.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jz.yihua.toutiao.common.bean.TtAccessToken;
import com.jz.yihua.toutiao.common.error.TtError;

/**
 * .
 * @author joey huang
 */
public class TtGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(TtError.class, new TtErrorAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
