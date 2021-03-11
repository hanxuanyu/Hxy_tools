package com.hxuanyu.mytools.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.utils.BitmapUtil;
import com.hxuanyu.mytools.utils.BlurBitmapUtil;
import com.hxuanyu.mytools.utils.LocalCacheUtil;


/**
 * @author Qiushui
 * @description 自定义模糊View类
 * @revision Xiarui 16.09.05
 */

public class BlurredView extends RelativeLayout {

    /*========== 全局相关 ==========*/

    //上下文对象
    private Context mContext;
    //透明最大值
    private static final int ALPHA_MAX_VALUE = 255;
    //最大模糊度(在0.0到25.0之间)
    private static final float BLUR_RADIUS = 25f;

    private LocalCacheUtil localCacheUtil;
    /*========== 图片相关 ==========*/
    private Animator animator1, animator2;
    private int currentpos = 1;
    //原图ImageView
    private ImageView mOriginImg;
    //模糊后的ImageView
    private ImageView mBlurredImg;

    private ImageView mBackgroundImg;
    //模糊后的Bitmap
    private Bitmap mBlurredBitmap;
    //原图Bitmap
    private Bitmap mOriginBitmap;


    /*========== 属性相关 ==========*/

    //是否禁用模糊效果
    private boolean isDisableBlurred;

    /*========== 四个构造函数 ==========*/

    public BlurredView(Context context) {
        super(context);
        initView(context);
    }

    public BlurredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttr(context, attrs);
    }

    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        initAttr(context, attrs);
    }

    /**
     * 初始化View
     *
     * @param context 上下文对象
     */
    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        mContext = context;
        localCacheUtil = new LocalCacheUtil(mContext);
        LayoutInflater.from(context).inflate(R.layout.blurredview, this);
        mOriginImg = (ImageView) findViewById(R.id.blurredview_origin_img);
        mBlurredImg = (ImageView) findViewById(R.id.blurredview_blurred_img);
        mBackgroundImg = findViewById(R.id.background_img);
        getCache();
        setImageView();
    }

    private void getCache() {
        Bitmap localorigbitmap = localCacheUtil.getBitmapFromLocal("originimg");
        Bitmap localblurbitmap = localCacheUtil.getBitmapFromLocal("bluredimg");
        if(localorigbitmap!=null&&localblurbitmap!=null){

            mOriginBitmap = localorigbitmap;
            mBlurredBitmap = localblurbitmap;
        }
    }

    /**
     * 初始化属性
     *
     * @param context 上下文对象
     * @param attrs   相关属性
     */
    private void initAttr(Context context, AttributeSet attrs) {
        //查找一些属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurredView);
        Drawable drawable = typedArray.getDrawable(R.styleable.BlurredView_src);
        //默认为false
        isDisableBlurred = typedArray.getBoolean(R.styleable.BlurredView_disableBlurred, false);
        //必须回收 方便重用
        typedArray.recycle();

        // 模糊图片
        if (null != drawable) {
            mOriginBitmap = BitmapUtil.drawableToBitmap(drawable);
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(context, mOriginBitmap, BLUR_RADIUS);
        }

        // 设置是否可见
        if (!isDisableBlurred) {
            mBlurredImg.setVisibility(VISIBLE);
        }
    }


    public Bitmap getBlurredBitmap(){
        return mBlurredBitmap;
    }
    public Bitmap getOriginBitmap(){
        return mOriginBitmap;
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurredBitmap 待模糊的图片
     */
    public void setBlurredImg(Bitmap blurredBitmap) {
        if (null != blurredBitmap) {
            mOriginBitmap = blurredBitmap;
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(mContext, blurredBitmap, BLUR_RADIUS);
            setCache();
            setImageView();
        }
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurDrawable 待模糊的图片
     */
    public void setBlurredImg(Drawable blurDrawable) {
        if (null != blurDrawable) {
            mOriginBitmap = BitmapUtil.drawableToBitmap(blurDrawable);
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(mContext, mOriginBitmap, BLUR_RADIUS);
            setCache();
            setImageView();
        }
    }

    private void setCache() {
        localCacheUtil.setBitmapToLocal("originimg",mOriginBitmap);
        localCacheUtil.setBitmapToLocal("bluredimg",mBlurredBitmap);
    }

    /**
     * 当所有子View出现后 设置相关内容
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setImageView();
    }

    /**
     * 填充ImageView
     */
    private void setImageView() {
        //Glide.with(mBackgroundImg).load(mOriginBitmap).diskCacheStrategy(DiskCacheStrategy.NONE).into(mBackgroundImg);
        DrawableCrossFadeFactory build = new DrawableCrossFadeFactory.Builder(2000).setCrossFadeEnabled(true).build();
        Glide.with(mBlurredImg).load(mBlurredBitmap).diskCacheStrategy(DiskCacheStrategy.NONE).transition(DrawableTransitionOptions.withCrossFade(build)).into(mBlurredImg);
        Glide.with(mOriginImg).load(mOriginBitmap).diskCacheStrategy(DiskCacheStrategy.NONE).transition(DrawableTransitionOptions.withCrossFade(build)).into(mOriginImg);

//        mBlurredImg.setImageBitmap(mBlurredBitmap);
//        mOriginImg.setImageBitmap(mOriginBitmap);
    }

    /**
     * 设置模糊程度
     *
     * @param level 模糊程度, 数值在 0~100 之间.
     */
    @SuppressWarnings("deprecation")
    public void setBlurredLevel(int level) {
        //超过模糊级别范围 直接抛异常
        if (level < 0 || level > 100) {
            throw new IllegalStateException("No validate level, the value must be 0~100");
        }

        //禁用模糊直接返回
        if (isDisableBlurred) {
            return;
        }

        //设置透明度
        mOriginImg.setAlpha((int) (ALPHA_MAX_VALUE - level * 2.55));
    }

    /**
     * 显示模糊图片
     */
    public void showBlurredView() {
        mBlurredImg.setVisibility(VISIBLE);
    }

    /**
     * 选择是否启动/禁止模糊效果
     *
     * @param isDisableBlurred 是否禁用模糊效果
     */
    @SuppressWarnings("deprecation")
    public void setBlurredable(boolean isDisableBlurred) {
        if (isDisableBlurred) {
            mOriginImg.setAlpha(ALPHA_MAX_VALUE);
            mBlurredImg.setVisibility(INVISIBLE);
        } else {
            mBlurredImg.setVisibility(VISIBLE);
        }
    }

    /**
     * 禁用模糊效果
     */
    @SuppressWarnings("deprecation")
    public void disableBlurredView() {
        isDisableBlurred = true;
        mOriginImg.setAlpha(ALPHA_MAX_VALUE);
        mBlurredImg.setVisibility(INVISIBLE);
    }

    /**
     * 启用模糊效果
     */
    public void enableBlurredView() {
        isDisableBlurred = false;
        mBlurredImg.setVisibility(VISIBLE);
    }
}
