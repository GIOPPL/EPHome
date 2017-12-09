package com.gioppl.ephome.sliding.login;

/**
 * Created by GIOPPL on 2017/11/28.
 */

public class LoginEntity {

    /**
     * state : true
     * id : 0
     */

    private boolean state;
    private int id;
    /**
     * uimage : null
     * uname : xing
     * upwd : 123123
     * iphone : 15822112995
     * email : 1017647773@qq.com
     * address : 天津
     * uid : 2
     */

    private String uimage;
    private String uname;
    private String upwd;
    private String iphone;
    private String email;
    private String address;
    private int uid;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
