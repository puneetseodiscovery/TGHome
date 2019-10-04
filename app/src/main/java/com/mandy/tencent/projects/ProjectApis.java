package com.mandy.tencent.projects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectApis {
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

    public class EnquiryVideo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("enquiry_id")
        @Expose
        private Integer enquiryId;
        @SerializedName("file")
        @Expose
        private String file;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getEnquiryId() {
            return enquiryId;
        }

        public void setEnquiryId(Integer enquiryId) {
            this.enquiryId = enquiryId;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("room")
        @Expose
        private String room;
        @SerializedName("t1")
        @Expose
        private String t1;
        @SerializedName("m2")
        @Expose
        private String m2;
        @SerializedName("t2")
        @Expose
        private String t2;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("dropdown")
        @Expose
        private String dropdown;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("enquiry_video")
        @Expose
        private List<EnquiryVideo> enquiryVideo = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getT1() {
            return t1;
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getM2() {
            return m2;
        }

        public void setM2(String m2) {
            this.m2 = m2;
        }

        public String getT2() {
            return t2;
        }

        public void setT2(String t2) {
            this.t2 = t2;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDropdown() {
            return dropdown;
        }

        public void setDropdown(String dropdown) {
            this.dropdown = dropdown;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<EnquiryVideo> getEnquiryVideo() {
            return enquiryVideo;
        }

        public void setEnquiryVideo(List<EnquiryVideo> enquiryVideo) {
            this.enquiryVideo = enquiryVideo;
        }

    }
}
