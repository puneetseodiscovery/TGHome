package com.mandy.tencent.myaccount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileApi {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("bankid")
        @Expose
        private String bankid;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("contact_no")
        @Expose
        private Object contactNo;
        @SerializedName("profile_image")
        @Expose
        private Object profileImage;
        @SerializedName("terms")
        @Expose
        private Integer terms;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBankid() {
            return bankid;
        }

        public void setBankid(String bankid) {
            this.bankid = bankid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getContactNo() {
            return contactNo;
        }

        public void setContactNo(Object contactNo) {
            this.contactNo = contactNo;
        }

        public Object getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(Object profileImage) {
            this.profileImage = profileImage;
        }

        public Integer getTerms() {
            return terms;
        }

        public void setTerms(Integer terms) {
            this.terms = terms;
        }

    }
}
