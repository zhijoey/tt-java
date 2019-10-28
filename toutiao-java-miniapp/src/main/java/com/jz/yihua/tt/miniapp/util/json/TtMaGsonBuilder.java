package com.jz.yihua.tt.miniapp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author joey huang
 */
public class TtMaGsonBuilder {
  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
