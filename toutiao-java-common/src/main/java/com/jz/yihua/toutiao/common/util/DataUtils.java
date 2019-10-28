package com.jz.yihua.toutiao.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  数据处理工具类
 * </pre>
 *
 * @author joey huang
 */
public class DataUtils {
  /**
   * 将数据中包含的secret字符使用星号替换，防止日志打印时被输出
   */
  public static <E> E handleDataWithSecret(E data) {
    E dataForLog = data;
    if(data instanceof String && StringUtils.contains((String)data, "&secret=")){
      dataForLog = (E) StringUtils.replaceAll((String)data,"&secret=\\w+&","&secret=******&");
    }
    return dataForLog;
  }
}
