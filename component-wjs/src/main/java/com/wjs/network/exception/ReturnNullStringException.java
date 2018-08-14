package com.wjs.network.exception;

/**
 * Created by WJS on 2017/7/24.
 */

public class ReturnNullStringException extends Exception
{
    public ReturnNullStringException()
    {
        super("服务器返回数据字符串为空");
    }
}
