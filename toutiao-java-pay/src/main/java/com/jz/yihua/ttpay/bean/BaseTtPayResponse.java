package com.jz.yihua.ttpay.bean;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * @author joey huang
 */
@ToString
@Data
public abstract class BaseTtPayResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("sub_code")
    private String subCode;

    @SerializedName("sub_msg")
    private String subMsg;

    private static final String SUCCESS_RESP_CODE = "10000";

    /**
     * 返回签名字符串
     * @return
     */
    protected abstract String getSignString();

    public boolean isSuccess() {
        return code.equals(SUCCESS_RESP_CODE);
    }

}
