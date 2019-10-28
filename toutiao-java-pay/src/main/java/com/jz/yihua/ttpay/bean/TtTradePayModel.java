package com.jz.yihua.ttpay.bean;

import lombok.Data;

/**
 * @Author: joey huang
 * @Date: 2019/10/23 16:09
 */
@Data
public class TtTradePayModel {

    private String uid;

    private String outOrderNo;

    private Long totalAmount;

    private String subject;

    private String body;

    private String tradeTime;

    private String validTime;

    private String notifyUrl;

    private String riskInfo;

    private String wxUrl;

    private String aliPayUrl;

}
