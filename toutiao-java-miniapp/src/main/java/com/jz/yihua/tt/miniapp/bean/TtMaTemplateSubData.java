package com.jz.yihua.tt.miniapp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 参考文档 https://developer.toutiao.com/dev/miniapp/uUTNz4SN1MjL1UzM
 * </pre>
 *
 * @author joey huang
 */
@Data
@NoArgsConstructor
public class TtMaTemplateSubData {

  private String value;


  public TtMaTemplateSubData(String value) {
    this.value = value;
  }


}
