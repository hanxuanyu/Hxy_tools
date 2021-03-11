package com.hxuanyu.mytools.beans;

public class DocDownLoadInfo {
    private String msg;
    private String dlink;
    private String result;

    public DocDownLoadInfo(String msg, String dlink, String result) {
        this.msg = msg;
        this.dlink = dlink;
        this.result = result;
    }

    public DocDownLoadInfo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDlink() {
        return dlink;
    }

    public void setDlink(String dlink) {
        this.dlink = dlink;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
