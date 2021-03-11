package com.hxuanyu.mytools.utils;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AliYunApiUtil {


    //超时时间
    private static final int TIMEOUT_IN_MILLIONS = 10000;
    private static final String appCode = "4b172bc8569a45ceb24743290e3f3787";

    public interface CallBack {
        void onRequestComplete(String result);
        void onRequestError(String result);
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr ,final AliYunApiUtil.CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr,appCode);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onRequestError(e.toString());
                    }
                }

            };
        }.start();
    }



    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr,String appCode) {

        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            Log.i("http", "原始" + urlStr);
            // String encodURL = URLEncoder.encode(urlStr, "UTF-8");
            String encodURL = Uri.encode(urlStr);
            encodURL = encodURL.replaceAll("%3A", ":");
            encodURL = encodURL.replaceAll("%2F", "/");
            encodURL = encodURL.replaceAll("%3F", "?");
            encodURL = encodURL.replaceAll("%26", "&");
            encodURL = encodURL.replaceAll("%3D", "=");

            url = new URL(encodURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Authorization","APPCODE "+appCode);
            if (conn.getResponseCode() == 200) {

                is = conn.getInputStream();

                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                if (baos.toString()==null||baos.toString().equals("")) {
                    Log.i("http", "result为空");
                } else {
                    Log.i("http", baos.toString());
                }
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return null;
    }
}
