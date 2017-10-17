package com.gioppl.ephome.policy;

/**
 * Created by GIOPPL on 2017/10/8.
 */

public class PolicyModel {
    private String title;
    private String msg;
    private String content;
    private String url;

    public PolicyModel(String title, String msg, String content, String url) {
        this.title = title;
        this.msg = msg;
        this.content = content;
        this.url = url;
    }

    public PolicyModel(String title, String msg, String content) {
        this.title = title;
        this.msg = msg;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
