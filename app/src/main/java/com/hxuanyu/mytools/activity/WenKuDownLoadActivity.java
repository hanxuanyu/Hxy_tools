package com.hxuanyu.mytools.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.hxuanyu.mytools.BaseActivity;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.beans.DocDownLoadInfo;
import com.hxuanyu.mytools.beans.DocInfoBean;
import com.hxuanyu.mytools.utils.ClipboardUtils;
import com.hxuanyu.mytools.utils.HttpUtils;
import com.hxuanyu.mytools.utils.MyToast;
import com.hxuanyu.mytools.utils.StatusBarUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WenKuDownLoadActivity extends BaseActivity {

    private Context mContext = this;
    private final String UserName = "471328503";
    private final String Password = "445226";
//private final String UserName = "438069966";
//    private final String Password = "960531";
    private EditText wkAddress;
    private TextView outInfo;
    private Button startDBtn;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_ku_down_load);
        initView();
        initEvent();
    }

    private void initEvent() {
        startDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outInfo.append("开始解析\n");
                startDBtn.setClickable(false);
                outInfo.append("校验输入链接...");
                //https://wenku.baidu.com/view/2506f4beabea998fcc22bcd126fff705cd175c27.html
                String input = wkAddress.getText().toString();
                Pattern pattern;
                pattern = Pattern.compile("(wenku|wk).baidu.com/view/([a-z0-9/]{20,50})");
                Matcher matcher = pattern.matcher(input);
                if(matcher.find()){
                    outInfo.append("成功\n");
                    String id = matcher.group(2);
                    outInfo.append("文档id为："+id+"\n");
                    getDownLinkLoadById(id);

                }
                else{
                    outInfo.append("失败\n");
                    MyToast.show(mContext,"好像输入的链接出了问题，请重新输入╭(╯^╰)╮");
                    startDBtn.setClickable(true);
                }

            }
        });
    }

    private void appendText(String info){
        String temp = outInfo.getText().toString();
        if(temp!=null){
            outInfo.setText(temp+info);
        }
    }

    private void getDownLinkLoadById(String id) {
        appendText("请求文档信息...");
        HttpUtils.doPostAsyn("http://139.224.236.108/post.php", "usrname="+UserName+"&usrpass="+Password+"&docinfo=" + id + "&taskid=up_down_doc1", new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                appendText("成功\n"+"获取下载页面...");
                final Gson gson = new Gson();
                DocInfoBean docInfo = gson.fromJson(result,DocInfoBean.class);
                if(docInfo.getResult().equals("succ")){
                    appendText("成功\n"+"解析下载页面数据...");
                    Pattern pattern = Pattern.compile("http://139.224.236.108/nocode.php\\?id=([0-9a-z]{32})");
                    Matcher matcher = pattern.matcher(docInfo.getUrl());
                    if(matcher.find()){
                        appendText("成功\n"+"获取下载链接...");
                        HttpUtils.doPostAsyn("http://139.224.236.108/downdoc.php", "vid=" + matcher.group(1) + "&taskid=directDown", new HttpUtils.CallBack() {
                            @Override
                            public void onRequestComplete(String result) {
                                appendText("成功\n"+"正在解析下载结果...");
                                if(result.contains("dlink")){
                                    appendText("成功\n"+"正在开始下载...");
                                    DocDownLoadInfo docDownLoadInfo = gson.fromJson(result,DocDownLoadInfo.class);


                                    final String url = docDownLoadInfo.getDlink();
                                    Snackbar.make(toolbar,"5秒后自动下载...点我复制下载链接~",Snackbar.LENGTH_LONG).setAction("复制", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ClipboardUtils.copyText(mContext,url);
                                        }
                                    }).setDuration(5000).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                        @Override
                                        public void onDismissed(Snackbar transientBottomBar, int event) {
                                            super.onDismissed(transientBottomBar, event);
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_VIEW);
                                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                            intent.setData(Uri.parse(url));
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onShown(Snackbar transientBottomBar) {
                                            super.onShown(transientBottomBar);
                                        }
                                    }).show();


                                    String out = docDownLoadInfo.getMsg();
                                    if(out!=null){
                                        outInfo.append(out+"\n");
                                    }
                                    startDBtn.setClickable(true);
                                }else{
                                    DocDownLoadInfo docDownLoadInfo = gson.fromJson(result,DocDownLoadInfo.class);
                                    String out = docDownLoadInfo.getMsg();
                                    if(out!=null){
                                        outInfo.append(out+"\n");
                                    }

                                    startDBtn.setClickable(true);
                                }
                            }

                            @Override
                            public void onRequestError(String result) {
                            }
                        });
                    }
                    else{
                        appendText("失败\n");
                        startDBtn.setClickable(true);
                    }
                }
                else{
                    appendText("失败\n");
                    startDBtn.setClickable(true);
                }
            }

            @Override
            public void onRequestError(String result) {
                appendText("失败\n");
                startDBtn.setClickable(true);
            }
        });
    }

    private void initView() {
        wkAddress = findViewById(R.id.wk_download_edit_text);
        outInfo = findViewById(R.id.wk_download_info);
        startDBtn = findViewById(R.id.wk_download_btn);
        toolbar = findViewById(R.id.main_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

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
