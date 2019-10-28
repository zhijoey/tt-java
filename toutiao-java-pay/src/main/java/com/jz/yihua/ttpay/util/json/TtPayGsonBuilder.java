package com.jz.yihua.ttpay.util.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author: Huang zhi
 * @Date: 2019/10/28 11:37
 */
public class TtPayGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .enableComplexMapKeySerialization();

    static {
        INSTANCE.disableHtmlEscaping();
    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
