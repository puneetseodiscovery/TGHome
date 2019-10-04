package com.mandy.tencent.controller;

import com.mandy.tencent.MessageApis;
import com.mandy.tencent.baseactivity.GetStatusApis;
import com.mandy.tencent.kotlin.pojo.GetMessageListApi;
import com.mandy.tencent.kotlin.pojo.SendMesage;
import com.mandy.tencent.login.LoginApi;
import com.mandy.tencent.myaccount.GetProfileApi;
import com.mandy.tencent.projects.ProjectApis;
import com.mandy.tencent.retrofit.ApiInterface;
import com.mandy.tencent.retrofit.ServiceGenerator;
import com.mandy.tencent.setting.UpdateAccountApis;
import com.mandy.tencent.setting.UploadImageApi;
import com.mandy.tencent.signup.SignUpApi;
import com.mandy.tencent.termsandcondition.TermsAndConditionApi;
import com.mandy.tencent.upgrade.UpgradeApis;
import com.mandy.tencent.uploadimages.UploadApis;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {

    public SignUp signUp;
    public Terms termsConditon;
    public Login login;
    public GetProfile getProfile;
    public UploadImage uploadImage;
    public GetStatus getStatus;
    public UpdateStatus updateStatus;
    public ChangePassword changePassword;
    public UploadFile uploadFile;
    public UpdateAccount updateAccount;
    public Projects projects;
    public SendMessage sendMessage;
    public GetMessageList getMessageList;


    /*+++++++++++++SIGN UP+++++++++++++++*/
    public Controller(SignUp signUp1) {
        signUp = signUp1;
    }

    public void setSignUp(String name, String bankid, String password, String CPassword, String email) {
        final ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<SignUpApi> call = apiInterface.signUp(name, bankid, email, password, CPassword);
        call.enqueue(new Callback<SignUpApi>() {
            @Override
            public void onResponse(Call<SignUpApi> call, Response<SignUpApi> response) {
                signUp.onSucess(response);
            }

            @Override
            public void onFailure(Call<SignUpApi> call, Throwable t) {
                signUp.error(t.getMessage());
            }
        });
    }

    public interface SignUp {
        void onSucess(Response<SignUpApi> response);

        void error(String error);
    }

    /*++++++++++++++++++++++++++++END++++++++++++++++++*/

    /*++++++++++++++++++++++Terms & Condition+++++++++++*/

    public Controller(Terms termsConditon1) {
        termsConditon = termsConditon1;
    }

    public void setTerms(String userId, final String terms) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<TermsAndConditionApi> call = apiInterface.terms(userId, terms);

        call.enqueue(new Callback<TermsAndConditionApi>() {
            @Override
            public void onResponse(Call<TermsAndConditionApi> call, Response<TermsAndConditionApi> response) {
                termsConditon.onSucess(response);
            }

            @Override
            public void onFailure(Call<TermsAndConditionApi> call, Throwable t) {
                termsConditon.error(t.getMessage());
            }
        });
    }

    public interface Terms {
        void onSucess(Response<TermsAndConditionApi> response);

        void error(String error);
    }
    /*+++++++++++++++++++++END+++++++++++++++++*/

    /*+++++++++++++++++++Login api+++++++++++++++*/
    public Controller(Login login1) {
        login = login1;
    }

    public void setLogin(String personalNumber, String passwrod) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<LoginApi> call = apiInterface.login(personalNumber, passwrod);
        call.enqueue(new Callback<LoginApi>() {
            @Override
            public void onResponse(Call<LoginApi> call, Response<LoginApi> response) {
                login.onSucess(response);
            }

            @Override
            public void onFailure(Call<LoginApi> call, Throwable t) {
                login.error(t.getMessage());
            }
        });
    }

    public interface Login {
        void onSucess(Response<LoginApi> response);

        void error(String error);
    }
    /*+++++++++++++++++++End++++++++++++++++*/

    /*++++++++++++++++++++++Get user Profile Details+++++++++*/

    public Controller(GetProfile getProfile1, GetStatus getStatus1) {
        getProfile = getProfile1;
        getStatus = getStatus1;
    }

    public void setGetProfile(String userId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProfileApi> call = apiInterface.getProfiles(userId);
        call.enqueue(new Callback<GetProfileApi>() {
            @Override
            public void onResponse(Call<GetProfileApi> call, Response<GetProfileApi> response) {
                getProfile.onSucess(response);
            }

            @Override
            public void onFailure(Call<GetProfileApi> call, Throwable t) {
                getProfile.error(t.getMessage());
            }
        });
    }

    public interface GetProfile {
        void onSucess(Response<GetProfileApi> response);

        void error(String error);
    }
    /*++++++++++++++++++++++++END++++++++++++++++*/

    /*++++++++++++++++++++Upload Image+++++++++++++++++++*/
    public Controller(UploadImage uploadImage1, GetProfile getProfile1, ChangePassword changePassword1, UpdateAccount updateAccount1) {
        uploadImage = uploadImage1;
        getProfile = getProfile1;
        changePassword = changePassword1;
        updateAccount = updateAccount1;
    }

    public void setUploadImage(String UserId, MultipartBody.Part part) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<UploadImageApi> call = apiInterface.uploadImage(UserId, part);
        call.enqueue(new Callback<UploadImageApi>() {
            @Override
            public void onResponse(Call<UploadImageApi> call, Response<UploadImageApi> response) {
                uploadImage.onSucessUpload(response);
            }

            @Override
            public void onFailure(Call<UploadImageApi> call, Throwable t) {
                uploadImage.error(t.getMessage());
            }
        });
    }

    public interface UploadImage {
        void onSucessUpload(Response<UploadImageApi> response);

        void error(String error);
    }
    /*+++++++++++++++++END+++++++++++++++++=*/

    /*+++++++++++++++++++Get status +++++++++++++*/

    public Controller(GetStatus getStatus1, GetProfile getProfile1) {
        getStatus = getStatus1;
        getProfile = getProfile1;
    }

    public void setGetStatus(String userId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetStatusApis> call = apiInterface.getStatus(userId);
        call.enqueue(new Callback<GetStatusApis>() {
            @Override
            public void onResponse(Call<GetStatusApis> call, Response<GetStatusApis> response) {
                getStatus.onSucessStatus(response);
            }

            @Override
            public void onFailure(Call<GetStatusApis> call, Throwable t) {
                getStatus.error(t.getMessage());
            }
        });
    }

    public interface GetStatus {
        void onSucessStatus(Response<GetStatusApis> response);

        void error(String error);

    }
    /*+++++++++++++++END++++++++++++*/

    /*++++++++++++++++++Update status++++++++++++*/

    public Controller(UpdateStatus updateStatus1) {
        updateStatus = updateStatus1;
    }

    public void setUpdateStatus(String userId, String type) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<UpgradeApis> call = apiInterface.updateStatus(userId, type);
        call.enqueue(new Callback<UpgradeApis>() {
            @Override
            public void onResponse(Call<UpgradeApis> call, Response<UpgradeApis> response) {
                updateStatus.onSucess(response);
            }

            @Override
            public void onFailure(Call<UpgradeApis> call, Throwable t) {
                updateStatus.error(t.getMessage());
            }
        });
    }

    public interface UpdateStatus {
        void onSucess(Response<UpgradeApis> response);

        void error(String error);
    }
    /*++++++++++++++++++END+++++++++++*/


    /*+++++++++++++++++++CHange Password++++++++++++++++++*/
    public Controller(ChangePassword getChangePassword) {
        changePassword = getChangePassword;
    }

    public void setChangePassword(String userId, String oldPassword, String newPasswor, String confirmPassword) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<MessageApis> call = apiInterface.passwordChange(userId, oldPassword, newPasswor, confirmPassword);
        call.enqueue(new Callback<MessageApis>() {
            @Override
            public void onResponse(Call<MessageApis> call, Response<MessageApis> response) {
                changePassword.onSucessChange(response);
            }

            @Override
            public void onFailure(Call<MessageApis> call, Throwable t) {
                changePassword.error(t.getMessage());
            }
        });
    }

    public interface ChangePassword {
        void onSucessChange(Response<MessageApis> response);

        void error(String error);
    }
    /*+++++++++++++++++++++END+++++++++++++++++++++++++++*/

    /*++++++++++++++Upload file+++++++++++++*/
    public Controller(UploadFile uploadFile1) {
        uploadFile = uploadFile1;
    }

    public void setUploadFile(String userId, String room, String m2, String t1, String t2, String dropDown, String comment, ArrayList<MultipartBody.Part> file) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<UploadApis> call = apiInterface.uploadData(userId, room, m2, t1, t2, dropDown, comment, file);
        call.enqueue(new Callback<UploadApis>() {
            @Override
            public void onResponse(Call<UploadApis> call, Response<UploadApis> response) {
                uploadFile.onSucess(response);
            }

            @Override
            public void onFailure(Call<UploadApis> call, Throwable t) {
                uploadFile.error(t.getMessage());
            }
        });
    }

    public interface UploadFile {
        void onSucess(Response<UploadApis> response);

        void error(String error);
    }
    /*++++++++++++++++++++++END++++++++++++++*/

    /*+++++++++++++++++++++Update profile details+++++++++++=*/

    public void setUpdateAccount(String userId, String name, String email) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<UpdateAccountApis> call = apiInterface.updateAccount(userId, name, email);
        call.enqueue(new Callback<UpdateAccountApis>() {
            @Override
            public void onResponse(Call<UpdateAccountApis> call, Response<UpdateAccountApis> response) {
                updateAccount.onSucessAccount(response);
            }

            @Override
            public void onFailure(Call<UpdateAccountApis> call, Throwable t) {
                updateAccount.error(t.getMessage());
            }
        });
    }

    public interface UpdateAccount {
        void onSucessAccount(Response<UpdateAccountApis> response);

        void error(String error);
    }

    /*+++++++++++++++++++++++END++++++++++++++++++*/

    /*+++++++++++++++++++++++++Get Projects+++++++++++++++*/
    public Controller(Projects projects1) {
        projects = projects1;
    }

    public void setProjects(String userId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProjectApis> call = apiInterface.project(userId);
        call.enqueue(new Callback<ProjectApis>() {
            @Override
            public void onResponse(Call<ProjectApis> call, Response<ProjectApis> response) {
                projects.onSucess(response);
            }

            @Override
            public void onFailure(Call<ProjectApis> call, Throwable t) {
                projects.error(t.getMessage());
            }
        });
    }

    public interface Projects {
        void onSucess(Response<ProjectApis> response);

        void error(String error);
    }
    /*++++++++++++++++END+++++++++++++++++*/

    /*++++++++++++++++++++++++++++Post Message+++++++++++*/

    public Controller(SendMessage sendMessage1, GetMessageList getMessageList1) {
        sendMessage = sendMessage1;
        getMessageList = getMessageList1;
    }

    public void setSendMessage(String userId, String Message) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<SendMesage> call = apiInterface.sendMessage(userId, Message);
        call.enqueue(new Callback<SendMesage>() {
            @Override
            public void onResponse(Call<SendMesage> call, Response<SendMesage> response) {
                sendMessage.onSucessSend(response);
            }

            @Override
            public void onFailure(Call<SendMesage> call, Throwable t) {
                sendMessage.error(t.getMessage());
            }
        });
    }

    public interface SendMessage {
        void onSucessSend(Response<SendMesage> response);

        void error(String error);
    }

    /*++++++++++++++ENd+++++++++++++++++=*/

    /*++++++++++++++++++++++++Get message list+++++++++++++++*/

    public void setGetMessageList(String userId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetMessageListApi> call = apiInterface.getMessageList(userId);
        call.enqueue(new Callback<GetMessageListApi>() {
            @Override
            public void onResponse(Call<GetMessageListApi> call, Response<GetMessageListApi> response) {
                getMessageList.onSucessList(response);
            }

            @Override
            public void onFailure(Call<GetMessageListApi> call, Throwable t) {
                getMessageList.error(t.getMessage());
            }
        });
    }

    public interface GetMessageList {
        void onSucessList(Response<GetMessageListApi> response);

        void error(String error);
    }

}
