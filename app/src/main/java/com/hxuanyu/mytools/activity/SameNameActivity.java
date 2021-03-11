package com.hxuanyu.mytools.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hxuanyu.mytools.BaseActivity;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.utils.HttpUtils;
import com.hxuanyu.mytools.utils.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

public class SameNameActivity extends BaseActivity {

    private EditText nameText;
    private TextView nameInfo;
    private Button startBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_name);
        initView();
        initEvent();
    }

    private void initEvent() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInfo.setText("");
                String name = nameText.getText().toString();
                if(name.matches("^([\\u4e00-\\u9fa5]){2,4}$")){
                    startBtn.setClickable(false);
                    appendText("\n您输入的名字为"+name+"\n正在开始解析...\n");
                    String url = "https://wechat.sxhuzhen.com/hz-service/common/querySameName";
                    HttpUtils.doPostAsyn(url, "searchName="+name, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            appendText("查询成功~\n");
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String countName = jsonObject.getString("countName");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyToast.show(SameNameActivity.this,"全国共有"+countName+"个"+name);
                                    }
                                });
                                appendText("全国共有"+countName+"个"+name);

                                startBtn.setClickable(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                startBtn.setClickable(true);
                            }

                        }

                        @Override
                        public void onRequestError(String result) {
                            startBtn.setClickable(true);
                        }
                    });
                }
                else{
                    MyToast.show(SameNameActivity.this, "请输入正确的姓名~");
                }
            }
        });
    }

    private void initView() {
        nameInfo = findViewById(R.id.same_name_info);
        nameText = findViewById(R.id.same_name_edit_text);
        startBtn = findViewById(R.id.same_name_btn);
        toolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    private void appendText(String info){
        String temp = nameInfo.getText().toString();
        nameInfo.setText(temp+info);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
