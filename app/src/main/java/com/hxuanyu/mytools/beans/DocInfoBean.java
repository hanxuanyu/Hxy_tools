package com.hxuanyu.mytools.beans;

public class DocInfoBean {
    private String url;
    private String result;

    public DocInfoBean(String url, String result) {
        this.url = url;
        this.result = result;
    }

    public DocInfoBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
