package com.gioppl.ephome.forum;

/**
 * Created by GIOPPL on 2017/10/24.
 */

public class EventBusForumModel {
    private String title;
    private String content;
    private String userName;
    private String msg;
    private String imageHead;

    public EventBusForumModel(String title, String content, String userName, String msg, String imageHead) {
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.msg = msg;
        this.imageHead = imageHead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }
}
