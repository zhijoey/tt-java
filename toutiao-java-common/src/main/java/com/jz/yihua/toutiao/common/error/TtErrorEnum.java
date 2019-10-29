package com.jz.yihua.toutiao.common.error;

import lombok.Getter;

/**
 * 头条小程序错误码
 *
 * @author joey huang
 */
@Getter
public enum TtErrorEnum {

  PARAM_ERROR(10010001, "输入参数错误");

  private int code;
  private String msg;

  TtErrorEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  /**
   * <pre>
   * 通过错误代码查找其中文含义.
   */
  public static String findMsgByCode(int code) {
    for (TtErrorEnum value : TtErrorEnum.values()) {
      if (value.code == code) {
        return value.msg;
      }
    }

    return null;
  }
}
