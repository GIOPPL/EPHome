package com.gioppl.ephome.sliding.userInfor;

/**
 * Created by GIOPPL on 2017/12/4.
 */

public class UpdateEntity {

    /**
     * uimage : image
     * uname : ???
     * upwd : 123
     * iphone : 12345678910
     * email : PPL@qq.com
     * address : ??
     * state : false
     * uid : 0
     */

    private String uimage;
    private String uname;
    private String upwd;
    private String iphone;
    private String email;
    private String address;
    private boolean state;
    private int uid;

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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
