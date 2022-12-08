package com.loginform.Utils;

import com.loginform.GetData.GetSignupData;
import com.loginform.GetData.GetUserListData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<GetSignupData> getSignupData(@Field("name")  String name,
                                      @Field("emailid")  String email,
                                      @Field("contact") String contact,
                                      @Field("gender") String gender,
                                      @Field("city") String city,
                                      @Field("password") String password,
                                      @Field("dob") String dob
                                      );

    @FormUrlEncoded
    @POST("login.php")
    Call<GetLoginData> getLoginData(  @Field("emailid")  String email,
                                      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<GetSignupData> getUpdateData(@Field("id")  String id,
                                      @Field("name")  String name,
                                      @Field("emailid")  String email,
                                      @Field("contact") String contact,
                                      @Field("gender") String gender,
                                      @Field("city") String city,
                                      @Field("password") String password,
                                      @Field("dob") String dob
    );
    @FormUrlEncoded
    @POST("getUserList.php")
    Call<GetUserListData> getUserDataData(@Field("type")  String type);
}
