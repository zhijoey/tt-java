package com.jz.yihua.tt.miniapp.bean;

import com.jz.yihua.tt.miniapp.util.json.TtMaGsonBuilder;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板消息.
 * 参考 https://developer.toutiao.com/dev/miniapp/uUTNz4SN1MjL1UzM
 *
 * @author joey huang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TtMaTemplateMessage implements Serializable {
  private static final long serialVersionUID = -3778212377032596529L;

  /**
   * 服务端API调用标识
   */
  private String accessToken;
    /**
   * 接收者（用户）的 openid.
   * <pre>
   * 参数：touser
   * 是否必填： 是
   * 描述： 接收者（用户）的 openid
   * </pre>
   */
  private String toUser;

  /**
   * 所需下发的模板消息的id.
   * <pre>
   * 参数：template_id
   * 是否必填： 是
   * 描述： 所需下发的模板消息的id
   * </pre>
   */
  private String templateId;

  /**
   * 点击消息卡片之后打开的小程序页面地址，空则无跳转
   * <pre>
   * 参数：page
   * 是否必填： 否
   * 描述： 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
   * </pre>
   */
  private String page;

  /**
   * 表单提交场景下，为 submit 事件带上的 formId
   * <pre>
   * 参数：form_id
   * 是否必填： 是
   * 描述： 表单提交场景下，为 submit 事件带上的 formId
   * </pre>
   */
  private String formId;

  /**
   * 模板内容，不填则下发空模板.
   * <pre>
   * 参数：data
   * 是否必填： 是
   * 描述： 模板内容，不填则下发空模板
   * </pre>
   */
  private List<TtMaTemplateSubData> data;

  public TtMaTemplateMessage addData(TtMaTemplateSubData datum) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(datum);

    return this;
  }

  public String toJson() {
    return TtMaGsonBuilder.create().toJson(this);
  }

}
