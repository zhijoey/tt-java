package com.jz.yihua.ttpay.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: joey huang
 * @Date: 2019/10/23 15:03
 */
public class TtPaySignUtil {

    private static final String EMPTY_STRING = "";

    public static String buildMd5WithSalt(Map<String, Object> dataMap, String salt) {
        String signStr = genSignStr(dataMap);
        return DigestUtils.md5Hex(signStr + salt);
    }

    private static String genSignStr(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        Set<String> entrySet = data.keySet();
        List<String> list = new ArrayList<String>(entrySet);
        Collections.sort(list);
        for (String key: list) {
            if (StringUtils.isEmpty(key) || data.get(key).equals(EMPTY_STRING) || Objects.isNull(data.get(key))) {
                continue;
            }
            sb.append(key);
            sb.append("=");
            sb.append(data.get(key));
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
