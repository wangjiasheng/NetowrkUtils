package com.wjs.network.task;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by WJS on 2016/10/28.
 */
public abstract class HttpTaskCallback<T>
{
    /**
     * 后台线程URL参数的修改
     */
    public String onModifyURL(Context context,String requestURL){return requestURL;};
    /**
     * 后台查询数据接口
     */
    public Map<String,String> onLoadParams(Context context, Map<String,String> params){return params;};
    /**
     * 后台加密线程
     * */
    public String onLockParams(String key,String params)
    {
        return params;
    }
    //UI线程-显示进度条
    public void onShowProgress(){};
    //后台线程-打印GET或zhePOST的URL和PARAMS
    public void onRequestURL(String url) {}
    //UI线程-成功回调
    public abstract void onSucess(T bean);
    //后台线程-解析JavaBean
    public Object onCreateBean(String requestResult){return requestResult;};
    //UI线程-失败回调
    public abstract void onFaield(Throwable ex);
    //UI线程-隐藏进度条
    public void onHideProgress(){};
    //UI线程-取消回掉
    public void onCancelled(){};
    //后台线程-定时回掉
    public void onBackground(long runtime){};
    //后台线程-延时回掉
    public void onSleeping(){};
    //后台线程-保存cookie
    public void onSaveCookie(List<String> cookies){};
    /**
     * 后台线程-读取cookie
     */
    public List<String> onLoadCookie()
    {
        return null;
    }
    /**
     * 网络不可用
     */
    public void networkCannotUse(){};
}