package com.jz.yihua.tt.miniapp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jz.yihua.toutiao.common.error.TtError;
import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.tt.miniapp.api.TtMaMsgService;
import com.jz.yihua.tt.miniapp.api.TtMaService;
import com.jz.yihua.tt.miniapp.bean.TtMaTemplateMessage;
import com.jz.yihua.tt.miniapp.constant.TtMaConstants;
import lombok.AllArgsConstructor;

/**
 * @Author: joey huang
 * @Date: 2019/10/23 12:24
 */
@AllArgsConstructor
public class TtMaMsgServiceImpl implements TtMaMsgService {

    private static final JsonParser JSON_PARSER = new JsonParser();
    private TtMaService ttMaService;

    @Override
    public void sendTemplateMsg(TtMaTemplateMessage templateMessage) throws TtErrorException {
        String responseContent = this.ttMaService.post(TEMPLATE_MSG_SEND_URL, templateMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(TtMaConstants.ERRCODE).getAsInt() != 0) {
            throw new TtErrorException(TtError.fromJson(responseContent));
        }
    }
}
