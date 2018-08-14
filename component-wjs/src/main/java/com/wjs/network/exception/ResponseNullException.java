package com.wjs.network.exception;

/**
 * Created by WJS on 2017/7/24.
 */

public class ResponseNullException extends Exception
{
    public ResponseNullException()
    {
        super("服务器返回数据为空");
    }
}
