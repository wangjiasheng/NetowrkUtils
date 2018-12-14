package com.example.wjs.myutitls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wjs.network.http.HttpUtils;
import com.wjs.network.task.HttpTask;
import com.wjs.network.task.HttpTaskCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView mText;
    HttpTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText=findViewById(R.id.mText);
        task=HttpTask.post(this, "http://www.360doc.com/content/15/0107/18/14106735_438945076.shtml", new HttpTaskCallback<String>() {
            @Override
            public void onSucess(String bean) {
                mText.setText(bean);
                Log.i("wjs_wjs","dddddddddddddd");
                mText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        task.retry();
                    }
                },1000);
            }
            @Override
            public void onFaield(Throwable ex) {

            }
        });



        String workDateFrom = "2018-12-01 08:00:00";
        String workDateTo = "2018-12-03 18:00:00";
        String offset = "0";//分页获取数据，0表示第一页
        String limit = "10";//每页10条数据
        loginDingDing();


    }
    public void loginDingDing(){
        String accessTokenUrl = "https://oapi.dingtalk.com/gettoken";
        String corpid = "15321810823";//替换成自己的corpid
        String secret = "Wang812330500";//替换成自己的corpsecret
        String requestUrl = accessTokenUrl + "?corpid="+corpid+"&corpsecret="+secret;
        HttpTask.get(this, requestUrl, new HttpTaskCallback() {
            @Override
            public void onSucess(Object bean) {
                try {
                    JSONObject jsonObject = new JSONObject((String) bean);
                    String msg = (String)jsonObject.get("errmsg");
                    if("ok".equals(msg)) {
                        String accessToken = (String)jsonObject.get("access_token");
                        Toast.makeText(MainActivity.this,"ddddddddddddddddddd",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFaield(Throwable ex) {

            }
        });
    }
}
