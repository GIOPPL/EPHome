package com.gioppl.ephome.network;

import java.util.List;

/**
 * Created by GIOPPL on 2017/11/19.
 */

public class ForumEntity {


    /**
     * name : 你需我求
     * data : [{"telephone":"","address":"","authorid":0,"dataline":""},{"telephone":"","address":"","subject":"test","message":"test6666:victory:\r\n","authorid":2,"dataline":"2017-11-19 20:40:50","author":"test"},{"telephone":"","address":"","subject":"","message":"sdfsdfsdfsdfsdfsdf呵呵哈哈哈或或或","authorid":3,"dataline":"2017-11-20 23:19:27","author":"aaaaaaaaaa"},{"telephone":"","address":"","subject":"AAAAAAAA","message":"ＨＨＨＨＨＨＨHhhh呵呵哈哈哈或或或\r\n","authorid":3,"dataline":"2017-11-20 23:20:01","author":"aaaaaaaaaa"},{"telephone":"","address":"","authorid":0,"dataline":""}]
     */

    private String name;
    private List<DataBean> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
}
