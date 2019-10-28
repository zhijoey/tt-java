package com.jz.yihua.tt.miniapp.api;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.tt.miniapp.bean.TtMaTemplateMessage;

/**
 * <pre>
 * 消息发送接口
 * </pre>
 *
 * @author joey huang
 */
public interface TtMaMsgService {
  String TEMPLATE_MSG_SEND_URL = "https://developer.toutiao.com/api/apps/game/template/send";

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: <a href="https://developer.toutiao.com/dev/miniapp/uUTNz4SN1MjL1UzM">发送模板消息</a>
   * 接口url格式：POST https://developer.toutiao.com/api/apps/game/template/send
   * </pre>
   */
  void sendTemplateMsg(TtMaTemplateMessage templateMessage) throws TtErrorException;



}
