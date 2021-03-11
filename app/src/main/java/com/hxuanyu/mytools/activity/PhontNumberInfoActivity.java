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
import com.hxuanyu.mytools.beans.PhoneInfoBean;
import com.hxuanyu.mytools.utils.AliYunApiUtil;
import com.hxuanyu.mytools.utils.MyToast;
import com.hxuanyu.mytools.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhontNumberInfoActivity extends BaseActivity {

    private ListView listPNumberInfo;
    private List<Map<String, String>> list = null;
    private Toolbar toolbar;
    private EditText phoneNumber;
    private Button searchBtn;
    private Context mContext;
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186、166</p>
     * <p>电信：133、153、173、177、180、181、189、199</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phont_number_info);
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
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.item_listview_info, new String[] { "info_name",
                "info_text" }, new int[] {
                R.id.info_name,
                R.id.info_text });
        listPNumberInfo.setAdapter(adapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNumber.getText().toString();
                if(number.matches(REGEX_MOBILE_EXACT)){
                    getPhoneNumInfo(number);
                }
                else{
                    MyToast.show(mContext,"输入的手机号码格式有误~");
                }
            }
        });
    }

    private void getPhoneNumInfo(String number) {
        String url = "https://api04.aliyun.venuscn.com/mobile?mobile="+number;

        AliYunApiUtil.doGetAsyn(url,  new AliYunApiUtil.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                Log.i("api",result);
                Gson gson = new Gson();
                PhoneInfoBean phoneInfoBean = gson.fromJson(result, PhoneInfoBean.class);

                if(phoneInfoBean.getRet()==200){
                    final List<Map<String, String>> backlist = new ArrayList<Map<String, String>>();
                    PhoneInfoBean.DataBean body = phoneInfoBean.getData();
                    String key = "info_name",value = "info_text";
                    Map<String, String> isp = new HashMap<String, String>();
                    isp.put(key,"运营商");
                    isp.put(value,body.getIsp());
                    backlist.add(isp);
                    Map<String, String> types = new HashMap<String, String>();
                    types.put(key,"网络类型");
                    types.put(value,body.getTypes());
                    backlist.add(types);
                    Map<String, String> prov = new HashMap<String, String>();
                    prov.put(key,"省份");
                    prov.put(value,body.getProv());
                    backlist.add(prov);
                    Map<String, String> city = new HashMap<String, String>();
                    city.put(key,"城市");
                    city.put(value,body.getCity());
                    backlist.add(city);
                    Map<String, String> cityCode = new HashMap<String, String>();
                    cityCode.put(key,"区号");
                    cityCode.put(value,body.getCity_code());
                    backlist.add(cityCode);
                    Map<String, String> zipCode = new HashMap<String, String>();
                    zipCode.put(key,"邮编");
                    zipCode.put(value,body.getZip_code());
                    backlist.add(zipCode);
                    Map<String, String> areaCode = new HashMap<String, String>();
                    areaCode.put(key,"城市编码");
                    areaCode.put(value,body.getArea_code());
                    backlist.add(areaCode);
                    Map<String, String> lat = new HashMap<String, String>();
                    lat.put(key,"经度");
                    lat.put(value,body.getLat());
                    backlist.add(lat);
                    Map<String, String> lnt = new HashMap<String, String>();
                    lnt.put(key,"纬度");
                    lnt.put(value,body.getLng());
                    backlist.add(lnt);

                    PhontNumberInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleAdapter adapter = new SimpleAdapter(mContext, backlist,
                                    R.layout.item_listview_info, new String[] { "info_name",
                                    "info_text" }, new int[] {
                                    R.id.info_name,
                                    R.id.info_text });
                            listPNumberInfo.setAdapter(adapter);
                        }
                    });
                }
            }

            @Override
            public void onRequestError(String result) {

            }
        });
        String result = "{" +
                "  \"ret\": 200," +
                "  \"msg\": \"success\"," +
                "  \"log_id\": \"62D7A76F-B827-40A6-AB4E-640254D4F992\"," +
                "  \"data\": {" +
                "    \"types\": \"中国移动 GSM\"," +
                "    \"lng\": \"113.264434\"," +
                "    \"city\": \"广州\"," +
                "    \"num\": 1591743," +
                "    \"isp\": \"移动\"," +
                "    \"area_code\": \"440100\"," +
                "    \"city_code\": \"020\"," +
                "    \"prov\": \"广东\"," +
                "    \"zip_code\": \"510000\"," +
                "    \"lat\": \"23.129162\"" +
                "  }" +
                "}";
    }

    private void initView() {
        phoneNumber = findViewById(R.id.input_search);
        searchBtn = findViewById(R.id.start_search);
        listPNumberInfo = findViewById(R.id.phone_info_list);
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
