package com.jz.yihua.toutiao.common.util.http;

/**
 * <pre>
 * http响应处理器.
 * @author joey huang
 * </pre>
 *
 * @param <R> 返回值类型
 * @author joey huang
 */
public interface ResponseHandler<R> {
  /**
   * 响应结果处理.
   *
   * @param r 要处理的对象
   */
  void handle(R r);
}
