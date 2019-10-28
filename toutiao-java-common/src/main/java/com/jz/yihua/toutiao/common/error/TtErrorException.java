package com.jz.yihua.toutiao.common.error;

/**
 * @author joey huang
 */
public class TtErrorException extends Exception {
  private static final long serialVersionUID = -4282771828217320365L;
  private TtError error;

  public TtErrorException(TtError error) {
    super(error.toString());
    this.error = error;
  }

  public TtErrorException(TtError error, Throwable cause) {
    super(error.toString(), cause);
    this.error = error;
  }

  public TtError getError() {
    return this.error;
  }


}
