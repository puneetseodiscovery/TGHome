package com.mandy.tencent.kotlin.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMessageListApi {
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("from_user")
        @Expose
        private Integer fromUser;
        @SerializedName("to_user")
        @Expose
        private Integer toUser;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("seen_status")
        @Expose
        private String seenStatus;
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

        public Integer getFromUser() {
            return fromUser;
        }

        public void setFromUser(Integer fromUser) {
            this.fromUser = fromUser;
        }

        public Integer getToUser() {
            return toUser;
        }

        public void setToUser(Integer toUser) {
            this.toUser = toUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSeenStatus() {
            return seenStatus;
        }

        public void setSeenStatus(String seenStatus) {
            this.seenStatus = seenStatus;
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

}
