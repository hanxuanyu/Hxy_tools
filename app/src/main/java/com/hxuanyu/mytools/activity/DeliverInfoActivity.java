package com.hxuanyu.mytools.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hxuanyu.mytools.BaseActivity;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.adapters.DeliverInfoAdapter;
import com.hxuanyu.mytools.beans.DeliverInfoBean;
import com.hxuanyu.mytools.utils.AliYunApiUtil;
import com.hxuanyu.mytools.utils.MyToast;
import com.hxuanyu.mytools.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class DeliverInfoActivity extends BaseActivity {

    private Context mContext;
    private EditText editText;
    private Button button;
    private DeliverInfoAdapter deliverInfoAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<DeliverInfoBean.ShowapiResBodyBean.DataBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_info);

        mContext = this;

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                if(!query.equals("")){
                    MyToast.show(mContext,"正在搜索单号为"+query+"的快递信息");
                    doQuery(query);
                }
            }
        });
    }

    private void doQuery(String query) {

        String url = "https://ali-deliver.showapi.com/showapi_expInfo?com=auto&nu="+query;
        AliYunApiUtil.doGetAsyn(url, new AliYunApiUtil.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                Log.i("api",result);
                Gson gson = new Gson();
                DeliverInfoBean deliverInfoBean = gson.fromJson(result,DeliverInfoBean.class);
                if(deliverInfoBean.getShowapi_res_code()==0){
                    final List<DeliverInfoBean.ShowapiResBodyBean.DataBean> backdataList = deliverInfoBean.getShowapi_res_body().getData();
                    if(backdataList!=null){
                        DeliverInfoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("apiBack",backdataList.get(0).getContext());
                                DeliverInfoAdapter deliverInfoAdapter = new DeliverInfoAdapter(backdataList,mContext);
                                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(deliverInfoAdapter);
                            }
                        });

                    }
                }
                else{
                    MyToast.show(mContext,deliverInfoBean.getShowapi_res_error());
                }

            }

            @Override
            public void onRequestError(String result) {
               DeliverInfoActivity.this.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       MyToast.show(mContext,"请求出错，请稍后重试");
                   }
               });
            }
        });
    }

    private void doQueryC(String query) {

        String result = "{" +
                "                      \"showapi_res_error\": \"\"," +
                "                      \"showapi_res_id\": \"1bd36d3ec4d1481885b0f1dc969410ac\"," +
                "                      \"showapi_res_code\": 0," +
                "                      \"showapi_res_body\": {\"update\":1561296704048,\"logo\":\"http://app2.showapi.com/img/expImg/shentong.jpg\",\"updateStr\":\"2019-06-23 21:31:44\",\"dataSize\":13,\"status\":4,\"tel\":\"400-889-5543\",\"showapi_fee_code\":-1,\"expSpellName\":\"shentong\",\"data\":[{\"time\":\"2019-06-20 13:20:48\",\"context\":\"已签收,签收人是（快递中心代收）先生/女士，风里来，雨里去，汗也撒泪也流，申通小哥一刻不停留。不求服务惊天下，但求好评动我心，给个好评呗！！如有疑问请联系派件员{0}{1}，感谢使用申通快递，期待再次为您服务\"},{\"time\":\"2019-06-20 09:16:04\",\"context\":\"山西晋中高校新区分部-理工近邻宝(18903413689,18903413689)-派件中\"},{\"time\":\"2019-06-20 08:59:07\",\"context\":\"已到达-山西晋中高校新区分部\"},{\"time\":\"2019-06-20 06:00:32\",\"context\":\"山西晋中公司-已发往-山西晋中高校新区分部\"},{\"time\":\"2019-06-19 19:35:02\",\"context\":\"山西太原转运中心-已发往-山西晋中公司\"},{\"time\":\"2019-06-19 19:22:31\",\"context\":\"已到达-山西太原转运中心\"},{\"time\":\"2019-06-18 00:39:55\",\"context\":\"浙江杭州航空部-已装袋发往-山西太原转运中心\"},{\"time\":\"2019-06-18 00:39:55\",\"context\":\"浙江杭州航空部-已进行装车扫描\"},{\"time\":\"2019-06-18 00:27:55\",\"context\":\"已到达-浙江杭州航空部\"},{\"time\":\"2019-06-17 22:12:58\",\"context\":\"浙江杭州西部公司-已发往-浙江杭州航空部\"},{\"time\":\"2019-06-17 22:12:58\",\"context\":\"浙江杭州西部公司-已进行装袋扫描\"},{\"time\":\"2019-06-17 20:25:59\",\"context\":\"浙江杭州西部公司-2号秤(0571-89915200)-已收件\"},{\"time\":\"2019-06-17 17:40:08\",\"context\":\"浙江杭州西部公司-曾晓宇-已收件\"}],\"msg\":\"查询成功\",\"mailNo\":\"3713403487255\",\"queryTimes\":1,\"ret_code\":0,\"flag\":true,\"expTextName\":\"申通快递\",\"possibleExpList\":[]}" +
                "                    }";
        Log.i("api",result);
        Gson gson = new Gson();
        DeliverInfoBean deliverInfoBean = gson.fromJson(result,DeliverInfoBean.class);
        ArrayList<DeliverInfoBean.ShowapiResBodyBean.DataBean> backdataList = (ArrayList<DeliverInfoBean.ShowapiResBodyBean.DataBean>) deliverInfoBean.getShowapi_res_body().getData();
        if(backdataList!=null){
            deliverInfoAdapter = new DeliverInfoAdapter(backdataList,mContext);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(deliverInfoAdapter);
        }
    }

    private void initData() {

        deliverInfoAdapter = new DeliverInfoAdapter(dataList,mContext);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(deliverInfoAdapter);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    private void initView() {
        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.deliver_info_recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         editText= findViewById(R.id.deliver_search_view);
         button = findViewById(R.id.search_deliver);

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
