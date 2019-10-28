package com.jz.yihua.ttpay.bean;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@ToString
@Data
public class BaseResponse<T extends BaseTtPayResponse> {

    @SerializedName("sign")
    private String sign;

    @SerializedName("response")
    private T response;

}
