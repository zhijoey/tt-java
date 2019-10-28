package com.jz.yihua.ttpay.bean;

import com.jz.yihua.toutiao.common.error.InvalidPayRequestException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@ToString
@Getter
@Setter
public abstract class BaseTtPayRequest {

    /**
     * 头条收银台参数加密
     * @return
     * @throws IOException
     */
    protected abstract Map<String, Object> encode(String merchantId, String appId, String secret) throws IOException;

    /**
     * 验证头条收银台参数有效性
     *
     * @throws InvalidPayRequestException
     */
    public abstract void valid(String appId, String merchantId) throws InvalidPayRequestException;

    /**
     * response泛型类型
     * @return
     */
    public abstract Type getResponseType();


}
