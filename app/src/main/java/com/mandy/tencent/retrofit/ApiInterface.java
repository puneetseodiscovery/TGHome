package com.mandy.tencent.retrofit;

import com.mandy.tencent.MessageApis;
import com.mandy.tencent.baseactivity.GetStatusApis;
import com.mandy.tencent.kotlin.pojo.GetMessageListApi;
import com.mandy.tencent.kotlin.pojo.SendMesage;
import com.mandy.tencent.login.LoginApi;
import com.mandy.tencent.myaccount.GetProfileApi;
import com.mandy.tencent.projects.ProjectApis;
import com.mandy.tencent.setting.UpdateAccountApis;
import com.mandy.tencent.setting.UploadImageApi;
import com.mandy.tencent.signup.SignUpApi;
import com.mandy.tencent.termsandcondition.TermsAndConditionApi;
import com.mandy.tencent.upgrade.UpgradeApis;
import com.mandy.tencent.uploadimages.UploadApis;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    //Sign up
    @POST("user_register")
    Call<SignUpApi> signUp(
            @Query("full_name") String full_name,
            @Query("bankId") String bankId,
            @Query("email") String email,
            @Query("password") String stpasswordring,
            @Query("c_password") String c_password
    );

    // terms & condition
    @POST("varify_conditions")
    Call<TermsAndConditionApi> terms(
            @Query("user_id") String userId,
            @Query("terms") String terms
    );

    //login api
    @POST("login")
    Call<LoginApi> login(
            @Query("personal_number") String personlNumber,
            @Query("password") String password
    );


    // get user details
    @POST("userdetails")
    Call<GetProfileApi> getProfiles(@Query("user_id") String userId
    );

    //upload image
    @Multipart
    @POST("profileimage")
    Call<UploadImageApi> uploadImage(
            @Query("user_id") String userId,
            @Part MultipartBody.Part part
    );

    //get status
    @GET("subscriptionStatus")
    Call<GetStatusApis> getStatus(
            @Query("user_id") String userId
    );

    // update the status   http://tghome.amrdev.site/api/UpdateSubscriptionStatus?user_id=39&subscription
    @POST("UpdateSubscriptionStatus")
    Call<UpgradeApis> updateStatus(
            @Query("user_id") String userId,
            @Query("subscription_status") String type
    );

    //change password
    @POST("changepassword")
    Call<MessageApis> passwordChange(
            @Query("user_id") String user_id,
            @Query("old_password") String old_password,
            @Query("password") String password,
            @Query("confirm_password") String confirm_password
    );

    //upload video adn data
    @Multipart
    @POST("videoEnquiry")
    Call<UploadApis> uploadData(
            @Query("user_id") String userId,
            @Query("room") String room,
            @Query("m2") String m2,
            @Query("t1") String t1,
            @Query("t2") String t2,
            @Query("dropdown") String dropdown,
            @Query("comment") String comment,
            @Part ArrayList<MultipartBody.Part> file
    );

    // update the user profile
    @POST("userupdate")
    Call<UpdateAccountApis> updateAccount(
            @Query("user_id") String userId,
            @Query("name") String name,
            @Query("email") String email
    );

    //my projects
    @GET("UserEnquiry")
    Call<ProjectApis> project(
            @Query("user_id") String userId
    );

    //send message
    @POST("message")
    Call<SendMesage> sendMessage(
            @Query("user_id") String userId,
            @Query("message") String message
    );

    @GET("chat")
    Call<GetMessageListApi> getMessageList(
            @Query("user_id") String userId
    );

}
