package com.jz.yihua.ttpay.bean;

import lombok.Getter;

/**
 * @Author: joey huang
 * @Date: 2019/10/23 15:08
 */
@Getter
public enum TtPayAppletVersionEnum {
    /**
     * 头条收银台版本号
     */
    APPLET_VERSION_1("1.0"),
    APPLET_VERSION_2("2.0"),
    APPLET_VERSION_2P("2.0+");


    private String code;

    TtPayAppletVersionEnum(String code) {

        this.code = code;
    }

}
