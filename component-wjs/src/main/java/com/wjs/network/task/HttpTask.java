package com.wjs.network.task;

import android.content.Context;
import android.os.AsyncTask;

import com.wjs.network.exception.LockParamsExeption;
import com.wjs.network.exception.ResponseNullException;
import com.wjs.network.exception.ReturnNullStringException;
import com.wjs.network.http.HttpUtils;
import com.wjs.network.json.HttpMethod;
import com.wjs.network.utils.NetworkUtils;
import com.wjs.network.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Created by 家胜 on 2016/6/29.
 */
public class HttpTask extends AsyncTask<Void,Void,Message>
{
    private HttpTaskCallback mHttpCallback;
    private String mRequestURL;
    private Map<String,String> mReqParams;
    private Context mContext;
    private HttpMethod mHttpMethod;
    public HttpTask(Context mContext, HttpMethod mHttpMethod, String mRequestURL, Map<String,String> mReqParams, HttpTaskCallback callback)
    {
        super();
        this.mContext =mContext;
        this.mHttpCallback =callback;
        this.mRequestURL =mRequestURL;
        this.mReqParams =mReqParams;
        this.mHttpMethod =mHttpMethod;
    }
    public HttpTask submit()
    {
        execute();
        return this;
    }
    public void cancle()
    {
        try
        {
            cancel(true);
            mContext = null;
            mHttpCallback = null;
            mRequestURL = null;
            if(mReqParams !=null) {
                mReqParams.clear();
            }
            mReqParams = null;
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null)
            {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
    }
    public static HttpTask post(Context context, String requestURL,HttpTaskCallback callback)
    {
        return post(context,requestURL,null,callback);
    }
    public static HttpTask post(Context context, String requestURL, Map<String,String> requestParam,HttpTaskCallback callback)
    {
        try {
            HttpTask httpTask = new HttpTask(context,HttpMethod.POST, requestURL, requestParam,callback).submit();
            return httpTask;
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(callback!=null)
            {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        return null;
    }
    public static HttpTask get(Context context, String requestURL,HttpTaskCallback callback)
    {
        return get(context,requestURL,null,callback);
    }
    public static HttpTask get(Context context, String requestURL,Map<String,String> requestParam,HttpTaskCallback callback)
    {
        try {
            HttpTask httpTask = new HttpTask(context,HttpMethod.GET, requestURL, requestParam,callback).submit();
            return httpTask;
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(callback!=null)
            {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            if(callback!=null) {
                callback.onHideProgress();
                callback.onFaield(ex);
            }
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if (mHttpCallback != null) {
                mHttpCallback.onShowProgress();
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null)
            {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null) {
                mHttpCallback.onHideProgress();
                mHttpCallback.onFaield(ex);
            }
        }
    }

    @Override
    protected Message doInBackground(Void... params)
    {
        Message message = new Message();
        try {
            if (mHttpCallback != null) {
                mRequestURL=mHttpCallback.onModifyURL(mContext,mRequestURL);
                mHttpCallback.onLoadParams(mContext, mReqParams);//添加本地参数
                mHttpCallback.onSleeping();//首先执行暂停等待操作,例如某些地方需要让progressbar显示的更久一些
            }
            long time = System.currentTimeMillis();//用于需要后台执行10s如果后台提前完成在onSleping(long)返回剩余时间
            if (NetworkUtils.isNetworkAvailable(mContext)) {//判断网络是否可用
                if (mRequestURL != null) {
                    return getRequestNetwork(time,message);
                } else {
                    message.setStatus(false);
                    message.setCode(1106);
                    message.setMessage("URL为空");
                    return message;
                }
            }
            else
            {
                message.setStatus(false);
                message.setCode(1005);
                message.setMessage("网络不可用");
                return message;
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            message.setStatus(false);
            message.setCode(1000);
            message.setMessage("NullPointerException");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setStatus(false);
            message.setCode(1001);
            message.setMessage(ex.getMessage());
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            message.setStatus(false);
            message.setCode(1002);
            message.setMessage(ex.getMessage());
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            message.setStatus(false);
            message.setCode(1003);
            message.setMessage(ex.getMessage());
        }
        return message;
    }
    @Override
    protected void onPostExecute(Message aVoid)
    {
        super.onPostExecute(aVoid);
        try
        {
            if(isCancelled())
            {
                return;
            }
            if (aVoid != null&&aVoid.isStatus())
            {
                if (mHttpCallback != null)
                {
                    mHttpCallback.onSucess(aVoid.getData());
                    mHttpCallback.onHideProgress();
                }
            }
            else
            {
                if (mHttpCallback != null)
                {
                    if(aVoid!=null) {
                        mHttpCallback.onFaield(new Exception(aVoid.getMessage()));
                    }
                    else
                    {
                        mHttpCallback.onFaield(new Exception("服务器返回数据为空"));
                    }
                    mHttpCallback.onHideProgress();
                }
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null)
            {
                mHttpCallback.onFaield(ex);
                mHttpCallback.onHideProgress();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        try {
            if (mHttpCallback != null) {
                mHttpCallback.onCancelled();
                mHttpCallback.onHideProgress();
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            if(mHttpCallback !=null)
            {
                mHttpCallback.onFaield(ex);
                mHttpCallback.onHideProgress();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
        catch (Error ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            mHttpCallback.onFaield(ex);
            mHttpCallback.onHideProgress();
        }
    }
    public String getParamsString(long time) throws LockParamsExeption {
        StringBuilder builder = new StringBuilder();
        if (mReqParams != null)
        {
            Set<Map.Entry<String, String>> set = mReqParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if (mHttpCallback != null) {
                    if(value!=null) {
                        value = mHttpCallback.onLockParams(key, value);
                        if (value == null) {
                            if (mHttpCallback != null) {
                                mHttpCallback.onBackground(System.currentTimeMillis() - time);
                            }
                            throw new LockParamsExeption();
                        }
                    }
                }
                if (builder.length() == 0) {
                    builder.append(key);
                    builder.append("=");
                    builder.append(value);
                } else {
                    builder.append("&");
                    builder.append(key);
                    builder.append("=");
                    builder.append(value);
                }
            }
            return builder.toString();
        }
        return null;
    }
    public Message getRequestNetwork(long time,Message message) throws LockParamsExeption, ResponseNullException, ReturnNullStringException {
        String requestResult = null;
        Response response = null;

        String param=getParamsString(time);
        if(StringUtils.isNotNull(param))
        {
            if (mHttpCallback != null) {
                if (mRequestURL.indexOf("?") > 0) {
                    mHttpCallback.onRequestURL(mRequestURL + "&" + param);
                } else {
                    mHttpCallback.onRequestURL(mRequestURL + "?" + param);
                }
                List<String> sCookie = null;
                if (mHttpCallback != null) {
                    sCookie = mHttpCallback.onLoadCookie();
                }
                if (mHttpMethod == HttpMethod.POST) {
                    response = HttpUtils.doPost(this, mRequestURL, param, sCookie, 10);
                } else {

                    if (mRequestURL.indexOf("?") > 0) {
                        response = HttpUtils.doGet(this, mRequestURL + "&" + param, sCookie, 10);
                    } else {
                        response = HttpUtils.doGet(this, mRequestURL + "?" + param, sCookie, 10);
                    }
                }
            }
        }
        else
        {
            if (mHttpCallback != null) {
                mHttpCallback.onRequestURL(mRequestURL);
            }
            List<String> sCookie = null;
            if (mHttpCallback != null) {
                sCookie = mHttpCallback.onLoadCookie();
            }
            if (mHttpMethod == HttpMethod.POST) {
                response = HttpUtils.doPost(this, mRequestURL, null, sCookie, 10);
            } else {
                response = HttpUtils.doGet(this, mRequestURL, sCookie, 10);
            }
        }
        if (response != null) {
            requestResult = response.getResult();
            List<String> mCookei = response.getCookie();
            if (mHttpCallback != null) {
                if (mCookei != null) {
                    mHttpCallback.onSaveCookie(mCookei);
                } else {
                    mHttpCallback.onSaveCookie(new ArrayList<String>());
                }
            }
            if (!isCancelled()) {
                if (StringUtils.isNotNull(requestResult)) {
                    if (mHttpCallback != null) {
                        if (!isCancelled()) {
                            Object t = mHttpCallback.onCreateBean(requestResult);
                            if (mHttpCallback != null) {
                                mHttpCallback.onBackground(System.currentTimeMillis() - time);
                            }
                            message.setStatus(true);
                            message.setData(t);
                            return message;
                        }
                    }
                } else {
                    if (mHttpCallback != null) {
                        mHttpCallback.onBackground(System.currentTimeMillis() - time);
                    }
                    throw new ReturnNullStringException();
                }
            }
        } else {
            if (mHttpCallback != null) {
                mHttpCallback.onBackground(System.currentTimeMillis() - time);
            }
            throw new ResponseNullException();
        }
       return null;
    }
}
