package com.jz.yihua.tt.miniapp.api.impl;

import com.jz.yihua.toutiao.common.error.TtErrorException;
import com.jz.yihua.tt.miniapp.api.TtMaService;
import com.jz.yihua.tt.miniapp.api.TtMaUserService;
import com.jz.yihua.tt.miniapp.bean.TtMaJscode2SessionResult;
import com.jz.yihua.tt.miniapp.bean.TtMaPhoneNumberInfo;
import com.jz.yihua.tt.miniapp.bean.TtMaUserInfo;
import com.jz.yihua.tt.miniapp.util.crypt.TtMaCryptUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author joey huang
 */
@AllArgsConstructor
public class TtMaUserServiceImpl implements TtMaUserService {
  private TtMaService service;

  @Override
  public TtMaJscode2SessionResult getSessionInfo(String jsCode) throws TtErrorException {
    return service.jsCode2SessionInfo(jsCode);
  }

  @Override
  public TtMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
    return TtMaUserInfo.fromJson(TtMaCryptUtils.decryptByPKCS7(sessionKey, encryptedData, ivStr));
  }

  @Override
  public TtMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
    return TtMaPhoneNumberInfo.fromJson(TtMaCryptUtils.decryptByPKCS7(sessionKey, encryptedData, ivStr));
  }

  @Override
  public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
    final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
    return generatedSignature.equals(signature);
  }

}
