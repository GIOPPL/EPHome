package com.gioppl.ephome.forum;

/**
 * Created by GIOPPL on 2017/10/6.
 */

public class ForumBean {
    /**
     * @type : com.avos.avoscloud.AVObject
     * objectId : 59d86b8d128fe1529c70d023
     * updatedAt : 2017-10-07T05:52:13.229Z
     * createdAt : 2017-10-07T05:52:13.229Z
     * className : Forum
     * serverData : {"@type":"java.util.concurrent.ConcurrentHashMap","add":"天津","content":"test content 0","url":"http://ac-rxsnxjjw.clouddn.com/9c2dc624a05360229896.jpg","id":13,"title":"test title 0","person":"PPL"}
     */

    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ServerDataBean getServerData() {
        return serverData;
    }

    public void setServerData(ServerDataBean serverData) {
        this.serverData = serverData;
    }

    public static class ServerDataBean {
        /**
         * @type : java.util.concurrent.ConcurrentHashMap
         * add : 天津
         * content : test content 0
         * url : http://ac-rxsnxjjw.clouddn.com/9c2dc624a05360229896.jpg
         * id : 13
         * title : test title 0
         * person : PPL
         */

        private String add;
        private String content;
        private String url;
        private int id;
        private String title;
        private String person;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }
    }
//    private String title;
//    private String content;
//    private String imageUrl;
//
//    public ForumBean(String title, String content, String imageUrl) {
//        this.title = title;
//        this.content = content;
//        this.imageUrl = imageUrl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

}
