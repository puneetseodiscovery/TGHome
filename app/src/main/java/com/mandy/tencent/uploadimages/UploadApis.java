package com.mandy.tencent.uploadimages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadApis {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("room")
        @Expose
        private String room;
        @SerializedName("m2")
        @Expose
        private String m2;
        @SerializedName("t1")
        @Expose
        private String t1;
        @SerializedName("t2")
        @Expose
        private String t2;
        @SerializedName("dropdown")
        @Expose
        private String dropdown;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("file")
        @Expose
        private String file;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getM2() {
            return m2;
        }

        public void setM2(String m2) {
            this.m2 = m2;
        }

        public String getT1() {
            return t1;
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getT2() {
            return t2;
        }

        public void setT2(String t2) {
            this.t2 = t2;
        }

        public String getDropdown() {
            return dropdown;
        }

        public void setDropdown(String dropdown) {
            this.dropdown = dropdown;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

    }
}
