package com.hxuanyu.mytools.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 三级缓存之本地缓存
 */
public class LocalCacheUtil {

    private String CACHE_PATH;

    public LocalCacheUtil(Context context) {
        CACHE_PATH = context.getExternalCacheDir().getPath();
    }

    /**
     * 从本地读取图片
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url){
        String fileName = null;//把图片的url当做文件名,并进行MD5加密
        try {
            fileName = url;
            File file=new File(CACHE_PATH,fileName);

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            Log.i("本地缓存","读取成功");
            return bitmap;
        } catch (Exception e) {
            Log.i("本地缓存","读取失败"+e.toString());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     * @param fileName
     * @param bitmap
     */
    public void setBitmapToLocal(String fileName,Bitmap bitmap){
        Log.i("本地缓存",fileName);
        try {
            File file=new File(CACHE_PATH,fileName);

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
            Log.i("本地缓存","缓存成功");
        } catch (Exception e) {
            Log.i("本地缓存","缓存失败"+e.toString());
            e.printStackTrace();
        }

    }
}