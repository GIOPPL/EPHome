package com.gioppl.ephome.forum;

/**
 * Created by GIOPPL on 2017/10/6.
 */

public class ForumBean {
    /**
     * telephone :
     * address :
     * authorid : 0
     * dataline :
     * subject : test
     * message : test6666:victory:

     * author : test
     */


    private String telephone;
    private String address;
    private int authorid;
    private String dataline;
    private String subject;
    private String message;
    private String author;

    public ForumBean(String telephone, String address, int authorid, String dataline, String subject, String message, String author) {
        this.telephone = telephone;
        this.address = address;
        this.authorid = authorid;
        this.dataline = dataline;
        this.subject = subject;
        this.message = message;
        this.author = author;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getDataline() {
        return dataline;
    }

    public void setDataline(String dataline) {
        this.dataline = dataline;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
