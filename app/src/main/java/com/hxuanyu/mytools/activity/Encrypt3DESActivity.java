package com.hxuanyu.mytools.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hxuanyu.mytools.BaseActivity;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.utils.EncryptUtil;
import com.hxuanyu.mytools.utils.StatusBarUtil;

public class Encrypt3DESActivity extends BaseActivity {

    private Button EncodeBtn;
    private Button DecodeBtn;
    private EditText EncodedText;
    private EditText DecodedText;
    private EditText KeyText;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {
        EncodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decodedText = DecodedText.getText().toString();
                String key  = KeyText.getText().toString();
                EncodedText.setText(EncryptUtil.Encryptor3DES(decodedText,key));
            }
        });

        DecodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encodedText = EncodedText.getText().toString();
                String key = KeyText.getText().toString();
                DecodedText.setText(EncryptUtil.Decryptor3DES(encodedText,key));
            }
        });
    }

    private void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        toolbar.setTitle("3DES加密工具");
    }

    private void initView() {
        EncodeBtn = findViewById(R.id.encode_btn);
        DecodeBtn = findViewById(R.id.decode_btn);
        EncodedText = findViewById(R.id.encoded_text);
        DecodedText = findViewById(R.id.decoded_text);
        KeyText = findViewById(R.id.key_text);
        toolbar = findViewById(R.id.en_toolbar);

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