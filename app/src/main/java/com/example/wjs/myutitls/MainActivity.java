package com.example.wjs.myutitls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wjs.network.task.HttpTask;
import com.wjs.network.task.HttpTaskCallback;

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
            }
            @Override
            public void onFaield(Throwable ex) {

            }
        });
        task.retry();
        task.retry();
        task.retry();
        task.retry();
        task.retry();
        task.retry();
    }
}
