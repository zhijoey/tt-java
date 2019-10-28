package com.jz.yihua.ttpay.bean;

import com.google.gson.annotations.SerializedName;
import com.jz.yihua.toutiao.common.util.json.TtGsonBuilder;
import com.jz.yihua.ttpay.util.TtPaySignUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


/**
 * @author joey huang
 */
@ToString
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TtTradeCreateResponse extends BaseTtPayResponse {

    @SerializedName("trade_no")
    private String tradeNo;

    @SerializedName("applet_params")
    private String appletParams;

    public void genCashDeskAppletParams(TtTradeCreateRequest req, String appId, String merchantId, String secret)  {
        String appletVersion = req.getAppletVersion();
        Map<String, String> appletMap = new HashMap<String, String>(4);

        switch (appletVersion) {
            case "1.0":
                appletMap.put("1.0", this.getCashDeskAppletParams1(req, appId, merchantId, secret));
                break;
            case "2.0":
                appletMap.put("2.0", this.getCashDeskAppletParams2(req, appId, merchantId, secret));
                break;
            case "2.0+":
                appletMap.put("1.0", this.getCashDeskAppletParams1(req, appId, merchantId, secret));
                appletMap.put("2.0", this.getCashDeskAppletParams2(req, appId, merchantId, secret));
                break;
            default:
                break;
        }

        appletParams = TtGsonBuilder.create().toJson(appletMap);
    }

    private String getCashDeskAppletParams1(TtTradeCreateRequest req, String appId, String merchantId, String secret) {

        Map<String, Object> appletParams = new HashMap<String, Object>(16);
        TtTradePayModel model = req.getTtTradePayModel();
        appletParams.put("app_id", appId);
        appletParams.put("sign_type", req.getSignType());
        appletParams.put("timestamp", "" + System.currentTimeMillis() / 1000);
        appletParams.put("trade_no", req.getTradeNo());
        appletParams.put("merchant_id", merchantId);
        appletParams.put("uid", model.getUid());
        appletParams.put("total_amount", model.getTotalAmount());
        appletParams.put("params",  "{\"url\":\"" + req.getParams() + "\"}");
        appletParams.put("sign", TtPaySignUtil.buildMd5WithSalt(appletParams, secret));
        appletParams.put("method", "tp.trade.confirm");
        appletParams.put("pay_type", req.getPayType());
        appletParams.put("pay_channel", req.getPayChannel());
        appletParams.put("risk_info", model.getRiskInfo());
        return TtGsonBuilder.create().toJson(appletParams);
    }

    private String getCashDeskAppletParams2(TtTradeCreateRequest req, String appId, String merchantId, String secret) {

        Map<String, Object> appletParams = new HashMap<String, Object>(32);
        TtTradePayModel model = req.getTtTradePayModel();
        appletParams.put("app_id", appId);
        appletParams.put("sign_type", req.getSignType());
        appletParams.put("timestamp", "" + System.currentTimeMillis() / 1000);
        appletParams.put("merchant_id", merchantId);
        appletParams.put("uid", model.getUid());
        appletParams.put("total_amount", model.getTotalAmount().toString());
        appletParams.put("out_order_no", model.getOutOrderNo());
        appletParams.put("product_code", req.getProductCode());
        appletParams.put("notify_url", model.getNotifyUrl());
        appletParams.put("trade_type", req.getTradeType());
        appletParams.put("payment_type", req.getPaymentType());
        appletParams.put("subject", model.getSubject());
        appletParams.put("body", model.getBody());
        appletParams.put("trade_time", model.getTradeTime());
        appletParams.put("valid_time", model.getValidTime());
        appletParams.put("currency", req.getCurrency());
        appletParams.put("version", req.getVersion());
        appletParams.put("alipay_url", model.getAliPayUrl());
        appletParams.put("wx_url", model.getWxUrl());
        appletParams.put("wx_type", req.getWxType());
        appletParams.put("sign", TtPaySignUtil.buildMd5WithSalt(appletParams, secret));
        appletParams.put("risk_info", model.getRiskInfo());
        return TtGsonBuilder.create().toJson(appletParams);
    }

    @Override
    protected String getSignString() {
        return "";
    }
}
