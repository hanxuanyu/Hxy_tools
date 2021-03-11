package com.hxuanyu.mytools.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hxuanyu.mytools.R;

public class EditTextDialog extends Dialog {


    private TextView dialogTittle,dialogTip;
    private String tittil,tip;
    private EditText dialogInput;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private Button dialogBtnYes,dialogBtnNo;




    public EditTextDialog(@NonNull Context context,String tittil,String tip) {
        super(context,R.style.MyDialog);
        this.tittil = tittil;
        this.tip = tip;
    }

    public EditTextDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edittext);


        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        dialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yesOnclickListener!=null){
                    yesOnclickListener.onYesClick(dialogInput.getText().toString());
                }
            }
        });
        dialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noOnclickListener!=null){
                    noOnclickListener.onNoClick();
                }
            }
        });

    }

    private void initData() {
        dialogTittle.setText(tittil);
        dialogTip.setText(tip);
    }

    private void initView() {
        dialogTittle = findViewById(R.id.dialog_tittle);
        dialogTip = findViewById(R.id.dialog_tip);
        dialogInput = findViewById(R.id.dialog_input);
        dialogBtnYes = findViewById(R.id.dialog_btn_yes);
        dialogBtnNo = findViewById(R.id.dialog_btn_no);
    }


    /**
     * 设置确定按钮的显示内容和监听
     *
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    /**
     * 设置取消按钮的显示内容和监听
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }
    public interface onNoOnclickListener {
        public void onNoClick();
    }


    public interface onYesOnclickListener {
        public void onYesClick(String qqNumber);
    }
}
