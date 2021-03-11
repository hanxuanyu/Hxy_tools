package com.hxuanyu.mytools.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.hxuanyu.mytools.BaseActivity;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.beans.IpInfoBean;
import com.hxuanyu.mytools.utils.AliYunApiUtil;
import com.hxuanyu.mytools.utils.MyToast;
import com.hxuanyu.mytools.utils.NetUtil;
import com.hxuanyu.mytools.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpInfoActivity extends BaseActivity {

    private ListView listViewIpInfo;
    private List<Map<String, String>> list = null;
    private Toolbar toolbar;
    private EditText ipAddress;
    private Button searchBtn;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip_info);
        mContext = this;
        initView();
        initData();

    }

    private void initData() {
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        list = new ArrayList<Map<String, String>>();
        Log.i("ipinfo", "==" + list.toString());
        // 定义SimpleAdapter适配器。
        // 使用SimpleAdapter来作为ListView的适配器，比ArrayAdapter能展现更复杂的布局效果。为了显示较为复杂的ListView的item效果，需要写一个xml布局文件，来设置ListView中每一个item的格式。
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.item_listview_info, new String[] { "info_name",
                "info_text" }, new int[] {
                R.id.info_name,
                R.id.info_text });
        listViewIpInfo.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipAddress.getText().toString();
                if (ip.matches("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))")){
                    getIpInfo(ip);
                }
                else if(ip.equals("")){
                    NetUtil.getNetIpSync(new NetUtil.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            getIpInfo(result);
                        }

                        @Override
                        public void onRequestError(String result) {

                        }
                    });

                }
                else{
                    MyToast.show(mContext,"输入的IP地址格式有误~");
                }
            }
        });

    }

    private void getIpInfo(String ip) {
        String url = "http://saip.market.alicloudapi.com/ip?ip="+ip;
        AliYunApiUtil.doGetAsyn(url,  new AliYunApiUtil.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                Log.i("api",result);
                Gson gson = new Gson();
                IpInfoBean ipInfoBean = gson.fromJson(result, IpInfoBean.class);

                if(ipInfoBean.getShowapi_res_code()==0){
                    final List<Map<String, String>> backlist = new ArrayList<Map<String, String>>();
                    IpInfoBean.ShowapiResBodyBean body = ipInfoBean.getShowapi_res_body();
                    String key = "info_name",value = "info_text";
                    Map<String, String> ipa = new HashMap<String, String>();
                    ipa.put(key,"IP:");
                    ipa.put(value,body.getIp());
                    backlist.add(ipa);
                    Map<String, String> isp = new HashMap<String, String>();
                    isp.put(key,"运营商");
                    isp.put(value,body.getIsp());
                    backlist.add(isp);
                    Map<String, String> continents = new HashMap<String, String>();
                    continents.put(key,"州");
                    continents.put(value,body.getContinents());
                    backlist.add(continents);
                    Map<String, String> country = new HashMap<String, String>();
                    country.put(key,"国家");
                    country.put(value,body.getCountry());
                    backlist.add(country);
                    Map<String, String> region = new HashMap<String, String>();
                    region.put(key,"省份");
                    region.put(value,body.getRegion());
                    backlist.add(region);
                    Map<String, String> city = new HashMap<String, String>();
                    city.put(key,"城市");
                    city.put(value,body.getCity());
                    backlist.add(city);
                    Map<String, String> county = new HashMap<String, String>();
                    county.put(key,"区县");
                    county.put(value,body.getCounty());
                    backlist.add(county);
                    Map<String, String> cityCode = new HashMap<String, String>();
                    cityCode.put(key,"邮编");
                    cityCode.put(value,body.getCity_code());
                    backlist.add(cityCode);
                    Map<String, String> lat = new HashMap<String, String>();
                    lat.put(key,"经度");
                    lat.put(value,body.getLat());
                    backlist.add(lat);
                    Map<String, String> lnt = new HashMap<String, String>();
                    lnt.put(key,"纬度");
                    lnt.put(value,body.getLnt());
                    backlist.add(lnt);

                    IpInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleAdapter adapter = new SimpleAdapter(mContext, backlist,
                                    R.layout.item_listview_info, new String[] { "info_name",
                                    "info_text" }, new int[] {
                                    R.id.info_name,
                                    R.id.info_text });
                            listViewIpInfo.setAdapter(adapter);
                        }
                    });
                }
            }

            @Override
            public void onRequestError(String result) {

            }
        });
        String result = "{" +
                "  \"showapi_res_code\": 0," +
                "  \"showapi_res_error\": \"\"," +
                "  \"showapi_res_body\": {" +
                "    \"region\": \"云南\"," +
                "    \"isp\": \"电信\"," +
                "    \"en_name\": \"China\"," +
                "    \"country\": \"中国\"," +
                "    \"city\": \"昆明\"," +
                "    \"ip\": \"106.61.10.210\"," +
                "    \"ret_code\": 0," +
                "    \"county\": \"官渡\"," +
                "    \"continents\": \"亚洲\"," +
                "    \"city_code\": \"530111\"," +
                "    \"lnt\": \"102.74362\"," +
                "    \"en_name_short\": \"CN\"," +
                "    \"lat\": \"25.01497\"" +
                "  }" +
                "}";

    }


    private void initView() {
        ipAddress = findViewById(R.id.input_search);
        searchBtn = findViewById(R.id.start_search);
        listViewIpInfo = (ListView) findViewById(R.id.ip_info_list);
        toolbar = findViewById(R.id.en_toolbar);
        setSupportActionBar(toolbar);
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
