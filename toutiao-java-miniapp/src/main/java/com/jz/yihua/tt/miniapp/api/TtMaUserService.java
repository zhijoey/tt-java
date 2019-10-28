package com.jz.yihua.tt.miniapp.api;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.tt.miniapp.bean.TtMaJscode2SessionResult;
import com.jz.yihua.tt.miniapp.bean.TtMaPhoneNumberInfo;
import com.jz.yihua.tt.miniapp.bean.TtMaUserInfo;

import java.util.Map;

/**
 * 用户信息相关操作接口.
 *
 * @author joey huang
 */
public interface TtMaUserService {

  /**
   * 获取登录后的session信息.
   *
   * @param jsCode 登录时获取的 code
   * @return .
   * @throws TtErrorException .
   */
  TtMaJscode2SessionResult getSessionInfo(String jsCode) throws TtErrorException;

  /**
   * 解密用户敏感数据.
   *
   * @param sessionKey    会话密钥
   * @param encryptedData 消息密文
   * @param ivStr         加密算法的初始向量
   */
  TtMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr);


  /**
   * 解密用户手机号信息.
   *
   * @param sessionKey    会话密钥
   * @param encryptedData 消息密文
   * @param ivStr         加密算法的初始向量
   * @return .
   */
  TtMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr);

  /**
   * 验证用户信息完整性.
   *
   * @param sessionKey 会话密钥
   * @param rawData    微信用户基本信息
   * @param signature  数据签名
   * @return .
   */
  boolean checkUserInfo(String sessionKey, String rawData, String signature);
}
