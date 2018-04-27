package com.example.devyani.drumbeatapplication.Interfaces;

import com.example.devyani.drumbeatapplication.model.BaseResponse;

import com.example.devyani.drumbeatapplication.model.GroupDetail.GroupDetailResponse;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserGroupResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by devyani on 16/4/18.
 */

public interface Api {

    String BASE_URL = "http://192.168.0.193/drumbeat/api/v2/";


    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("users/login")
    Call<UserGroupResponse> getLoginApi(@Field("email") String email,
                              @Field("password") String password,
                              @Field("device_type") String device_type,
                              @Field("device_token") String device_token);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("users/register")
    Call<UserGroupResponse> getRegistration(@Field("first_name") String FName,
                                  @Field("last_name") String LName,
                                  @Field("email") String Email,
                                  @Field("password") String Password,
                                  @Field("device_type") String DeviceType,
                                  @Field("device_token") String Device_token);


    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("users/edit")
    Call<BaseResponse> getEditApi(@Header("Id") Integer id,
                             @Header("Accesstoken") String token,
                             @Field("first_name") String firstname,
                             @Field("last_name") String lastname,
                             @Field("email") String email);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @POST("users/logout")
    Call<BaseResponse> getLogoutApi(@Header("Id") int id,
                               @Header("Accesstoken") String Accesstoken);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("users/changePassword")
    Call<BaseResponse> changePassword(@Header("Id") int id,
                                 @Header("Accesstoken") String Accesstoken,
                                 @Field("old_password") String oldpassword,
                                 @Field("new_password") String newpassword);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("groups/add")
    Call<BaseResponse> createpostapi(@Header("Id") int id,
                                         @Header("Accesstoken") String Accesstoken,
                                         @Field("name") String Name,
                                         @Field("latitude") String lattitude,
                                         @Field("longitude") String longitude,
                                         @Field("geofence_radius") int radios,
                                         @Field("location") String location);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("groups/user")
    Call<UserGroupResponse> creategroupapi(@Header("Id") int id,
                                           @Header("Accesstoken") String Accesstoken,
                                           @Field("user_id") int userid,
                                           @Field("page") String page);


    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("groups/all")
    Call<UserGroupResponse> createboardapi(@Header("Id") int id,
                                           @Header("Accesstoken") String Accesstoken,
                                           @Field("page") int page,
                                           @Field("lat") String lat,
                                           @Field("lng") String lng);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("groups/details")
    Call<GroupDetailResponse> alldetailsapi(@Header("Id") int id,
                                            @Header("Accesstoken") String Accesstoken,
                                            @Field("group_id") int groupid);

    @Headers("Xapi: 743e9b4fd90acc4e468cb78d716ce23e")
    @FormUrlEncoded
    @POST("users/followUnfollow")
    Call<BaseResponse> followunfollow(@Header("Id") int id,
                                      @Header("Accesstoken") String Accesstoken,
                                      @Field("other_user_id") int otheruserid);
}
