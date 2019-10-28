package com.jz.yihua.ttpay.bean;

import com.google.gson.reflect.TypeToken;
import com.jz.yihua.toutiao.common.error.InvalidPayRequestException;
import com.jz.yihua.toutiao.common.util.json.TtGsonBuilder;
import com.jz.yihua.ttpay.constant.TtPayConstants;
import com.jz.yihua.ttpay.util.TtPayParamsUtil;
import com.jz.yihua.ttpay.util.TtPaySignUtil;
import lombok.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author joey huang
 */
@ToString
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TtTradeCreateRequest extends BaseTtPayRequest {

    private String method;

    private String format;

    private String charset;

    private String signType;

    private String path;

    private String timestamp;

    private String appletVersion;

    private String version;

    private String uidType;

    private String currency;

    private String tradeType;

    private String productCode;

    private String paymentType;

    private String payType;

    private String params;

    private String productID;

    private String payChannel;

    private String payDiscount;

    private String serviceFee;

    private String wxType;

    private String extParam;

    private String tradeNo;

    private TtTradePayModel ttTradePayModel;

    private TtTradeCreateRequest(TtPayAppletVersionEnum ttPayAppletVersion) {

        this.appletVersion = ttPayAppletVersion.getCode();
        this.method = TtPayConstants.PAY_DEFAULT_METHOD;
        this.format = TtPayConstants.PAY_DEFAULT_FORMAT;
        this.charset = TtPayConstants.PAY_DEFAULT_CHARSET;
        this.signType = TtPayConstants.PAY_DEFAULT_SIGN_TYPE;
        this.timestamp = TtPayConstants.EMPTY_STRING + System.currentTimeMillis() / 1000;
        this.version = TtPayConstants.PAY_DEFAULT_VERSION;
        this.path = TtPayConstants.PAY_DEFAULT_PATH;
        this.currency = TtPayConstants.PAY_DEFAULT_CURRENCY;
        this.tradeType = TtPayConstants.PAY_DEFAULT_TRADE_TYPE;
        this.productCode = TtPayConstants.PAY_DEFAULT_PRODUCT_CODE;
        this.serviceFee = TtPayConstants.EMPTY_STRING;
        if (this.appletVersion.equals(TtPayAppletVersionEnum.Applet_Version_1.getCode())) {
            setParams4AppletVersion1();
        } else if (this.appletVersion.equals(TtPayAppletVersionEnum.Applet_Version_2.getCode())) {
            setParams4AppletVersion2();
        } else {
            setParams4AppletVersion1();
            setParams4AppletVersion2();
        }


    }

    private void setParams4AppletVersion1(){
        this.payType = TtPayConstants.PAY_VERSION1_PAY_TYPE;
        this.payChannel = TtPayConstants.PAY_VERSION1_PAY_CHANNEL;
    }

    private void setParams4AppletVersion2() {
        this.paymentType = TtPayConstants.PAY_VERSION2_PAYMENT_TYPE;
        this.wxType = TtPayConstants.WECHAT_TRADE_TYPE_H5;

    }

    public static TtTradeCreateRequest create(TtPayAppletVersionEnum appletVersion){
        return new TtTradeCreateRequest(appletVersion);
    }

    @Override
    public Map<String, Object> encode(String merchantId, String appId, String secret) {
        Map<String, Object> bizContent = new HashMap<>(16);
        bizContent.put("out_order_no", ttTradePayModel.getOutOrderNo());
        bizContent.put("uid", ttTradePayModel.getUid());
        bizContent.put("uid_type", uidType);
        bizContent.put("merchant_id", merchantId);
        bizContent.put("total_amount", ttTradePayModel.getTotalAmount());
        bizContent.put("currency", currency);
        bizContent.put("subject", ttTradePayModel.getSubject());
        bizContent.put("body", ttTradePayModel.getBody());
        bizContent.put("product_code", productCode);
        bizContent.put("payment_type", paymentType);
        bizContent.put("trade_time", ttTradePayModel.getTradeTime());
        bizContent.put("valid_time", ttTradePayModel.getValidTime());
        bizContent.put("notify_url", ttTradePayModel.getNotifyUrl());
        bizContent.put("service_fee", serviceFee);
        bizContent.put("risk_info", ttTradePayModel.getRiskInfo());
        String bizContentJson = TtGsonBuilder.create().toJson(bizContent);

        Map<String, Object> signParams = new HashMap<>(16);
        signParams.put("app_id", appId);
        signParams.put("method", method);
        signParams.put("format", format);
        signParams.put("charset", charset);
        signParams.put("sign_type", signType);
        signParams.put("timestamp", timestamp);
        signParams.put("version", version);
        signParams.put("biz_content", bizContentJson);
        signParams.put("sign", TtPaySignUtil.buildMd5WithSalt(signParams, secret));
        return signParams;
    }

    @Override
    public void valid(String appId, String merchantId) throws InvalidPayRequestException {
        TtPayParamsUtil.checkAppId(appId);
        if (method == null || !method.equals(TtPayConstants.PAY_DEFAULT_METHOD)) {
            throw new InvalidPayRequestException("invalid param: method");
        }
        TtPayParamsUtil.checkOutOrderNo(ttTradePayModel.getOutOrderNo());
        TtPayParamsUtil.checkUId(ttTradePayModel.getUid());
        TtPayParamsUtil.checkMerchantId(merchantId);
        TtPayParamsUtil.checkTotalAmount(ttTradePayModel.getTotalAmount());
        TtPayParamsUtil.checkCurrency(currency);
        TtPayParamsUtil.checkSubject(ttTradePayModel.getSubject());
        TtPayParamsUtil.checkBody(ttTradePayModel.getBody());
        TtPayParamsUtil.checkTradeTime(ttTradePayModel.getTradeTime());
        TtPayParamsUtil.checkValidTime(ttTradePayModel.getValidTime());
        TtPayParamsUtil.checkNotifyUrl(ttTradePayModel.getNotifyUrl());
        TtPayParamsUtil.checkRiskInfo(ttTradePayModel.getRiskInfo());
        if( appletVersion.equals(TtPayAppletVersionEnum.Applet_Version_2.getCode())
                || appletVersion.equals(TtPayAppletVersionEnum.Applet_Version_2p.getCode()) ) {
            TtPayParamsUtil.checkProductCode(productCode);
            TtPayParamsUtil.checkPaymentType(paymentType);
            TtPayParamsUtil.checkTradeType(tradeType);
        }
    }

    @Override
    public Type getResponseType() {
        return new TypeToken<BaseResponse<TtTradeCreateResponse>>() {
        }.getType();
    }

}
