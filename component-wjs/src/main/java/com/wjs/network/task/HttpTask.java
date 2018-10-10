package com.wjs.network.task;

import android.content.Context;
import android.os.AsyncTask;

import com.wjs.network.exception.LockParamsExeption;
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
public class HttpTask extends AsyncTask<Void,Void,Message> {
    private HttpTaskCallback mHttpCallback;
    private String mRequestURL;
    private Map<String,String> mReqParams;
    private Context mContext;
    private HttpMethod mHttpMethod;
    private long time=-1;
    public HttpTask(Context mContext, HttpMethod mHttpMethod, String mRequestURL, Map<String,String> mReqParams, HttpTaskCallback callback) {
        super();
        this.mContext =mContext;
        this.mHttpCallback =callback;
        this.mRequestURL =mRequestURL;
        this.mReqParams =mReqParams;
        this.mHttpMethod =mHttpMethod;
    }
    public HttpTask submit() {
        executeOnExecutor(ThreadPoolUtils.getService());
        return this;
    }
    public void retry(){
        HttpTask httpTask = new HttpTask(mContext,HttpMethod.POST, mRequestURL, mReqParams,mHttpCallback).submit();
    }
    public void cancle() {
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
    public static HttpTask post(Context context, String requestURL,HttpTaskCallback callback) {
        return post(context,requestURL,null,callback);
    }
    public static HttpTask post(Context context, String requestURL, Map<String,String> requestParam,HttpTaskCallback callback) {
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
    public static HttpTask get(Context context, String requestURL,HttpTaskCallback callback) {
        return get(context,requestURL,null,callback);
    }
    public static HttpTask get(Context context, String requestURL,Map<String,String> requestParam,HttpTaskCallback callback) {
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
    protected Message doInBackground(Void... params) {
        Message message = new Message();
        try {
            if (NetworkUtils.isNetworkAvailable(mContext)) {//判断网络是否可用
                time = System.currentTimeMillis();//用于需要后台执行10s如果后台提前完成在onSleping(long)返回剩余时间
                if (mHttpCallback != null) {
                    mRequestURL=mHttpCallback.onModifyURL(mContext,mRequestURL);//修改网络请求中的通配符
                    mHttpCallback.onLoadLocalParams(mContext, mReqParams);//添加本地参数
                    mHttpCallback.onSleeping();//首先执行暂停等待操作,例如某些地方需要让progressbar显示的更久一些
                }
                if (mRequestURL != null) {
                    return getRequestNetwork(message);
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
    protected void onPostExecute(Message aVoid) {
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
    public void lockParams() throws LockParamsExeption {
        if(mReqParams!=null) {
            Set<Map.Entry<String, String>> set = mReqParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if (mHttpCallback != null && value != null) {
                    value = mHttpCallback.onLockParams(key, value);
                    if (value == null) {
                        if (mHttpCallback != null) {
                            mHttpCallback.onBackground(System.currentTimeMillis() - time);
                        }
                        throw new LockParamsExeption();
                    }
                }
            }
        }
    }
    public void signParams(){
        if(mHttpCallback!=null&&mReqParams!=null){
            mReqParams=mHttpCallback.signParams(mReqParams);
        }
    }
    public String getParamsString() {
        StringBuilder builder = new StringBuilder();
        if (mReqParams != null)
        {
            Set<Map.Entry<String, String>> set = mReqParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
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
    public String getRequestURL(String param) throws LockParamsExeption {
        String requestURL=null;
        if(StringUtils.isNotNull(param))
        {
            if(mHttpCallback!=null){
                if (mRequestURL.indexOf("?") > 0) {
                    requestURL=mRequestURL + "&" + param;
                } else {
                    requestURL=mRequestURL + "?" + param;
                }
            }
        }
        else{
            requestURL=mRequestURL;
        }
        if (mHttpCallback != null) {
            mHttpCallback.onRequestURL(requestURL);
        }
        return requestURL;
    }
    public String loadCookie(){
       String sCookie = null;
        if (mHttpCallback != null) {
            sCookie = mHttpCallback.onLoadCookie();
        }
        return sCookie;
    }
    public Response requestNetwork(String requestURL,String param,String sCookie){
        Response response = null;
        if (mHttpMethod == HttpMethod.POST) {
            response = HttpUtils.doPost(this, mRequestURL, param, sCookie, 10);
        } else {
            response = HttpUtils.doGet(this, requestURL, sCookie, 10);
        }
        return response;
    }
    public void saveCookie(Response response){
        List<String> mCookei = response.getCookie();
        if (mHttpCallback != null) {
            if (mCookei != null) {
                mHttpCallback.onSaveCookie(mCookei);
            } else {
                mHttpCallback.onSaveCookie(new ArrayList<String>());
            }
        }
    }
    public Object createBean(String requestResult){
        if (mHttpCallback != null) {
            if (!isCancelled()) {
                Object t = mHttpCallback.onCreateBean(requestResult);
              return t;
            }
        }
        return null;
    }
    public Message getRequestNetwork(Message message) throws LockParamsExeption, ReturnNullStringException {
        lockParams();
        signParams();
        String param=getParamsString();
        String requestURL=getRequestURL(param);
        String sCookie=loadCookie();
        Response response = requestNetwork(requestURL,param,sCookie);  //Resopnse不会为空的
        String requestResult = response.getResult();
        saveCookie(response);
        if (!isCancelled()) {
            if (StringUtils.isNotNull(requestResult)) {
                Object t=createBean(requestResult);
                if (mHttpCallback != null) {
                    mHttpCallback.onBackground(System.currentTimeMillis() - time);
                }
                message.setStatus(true);
                message.setData(t);
                return message;
            } else {
                if (mHttpCallback != null) {
                    mHttpCallback.onBackground(System.currentTimeMillis() - time);
                }
                throw new ReturnNullStringException();
            }
        }
       return null;
    }
}
