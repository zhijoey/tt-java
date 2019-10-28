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
    Applet_Version_1("1.0"),
    Applet_Version_2("2.0"),
    Applet_Version_2p("2.0+");


    private String code;

    TtPayAppletVersionEnum(String code) {

        this.code = code;
    }

}
