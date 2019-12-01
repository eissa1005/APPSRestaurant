package com.example.myrestaurant.API;

import com.example.myrestaurant.Model.Response.AddonModel;
import com.example.myrestaurant.Model.Response.FoodsModel;
import com.example.myrestaurant.Model.Response.MenuModel;
import com.example.myrestaurant.Model.Response.RestauranrModel;
import com.example.myrestaurant.Model.Response.SizeModel;
import com.example.myrestaurant.Model.Response.User;
import com.example.myrestaurant.Model.Response.UserResponse;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface APIService {


    @GET("GetUser")
    Call<UserResponse> GetUser(@Query("key") String key,
                               @Query("fbid")String fbid);


    @GET("GetUserByPhone")
    Observable<UserResponse> GetUserByPhone(@Query("key") String key,
                                     @Query("userPhone")String userPhone);


    @GET("GetAllRestaurant")
    Observable<RestauranrModel> getRestaurant(@Query("key") String key);

    @GET("Menu")
    Observable<MenuModel> getCategories(@Query("key") String apiKey,
                                        @Query("RestaurantId") int restaurantId);

    @GET("Foods")
    Observable<FoodsModel> getFoods(@Query("key")String key,
                                    @Query("MenuId")int MenuId);

    @GET("Size")
    Observable<SizeModel> getSizeOfFoods(@Query("key")String key,
                                   @Query("FoodId")int FoodId);

    @GET("Addon")
    Observable<AddonModel> getAddonOfFoods(@Query("key")String key,
                                    @Query("FoodId")int FoodId);

    @FormUrlEncoded
    @POST("updateUser")
    Observable<User> UpdateUser(@Field("key")String key,
                                @PartMap Map<String,User> userItem);

    @FormUrlEncoded
    @POST("updateUser")
    Call<User> updateUser(@Field("key")String key,
                          @Field("FBID")String FBID,
                          @Field("userName")String userName,
                          @Field("userPhone")String userPhone,
                          @Field("address")String address,
                          @Field("userPhoto")String userPhoto,
                          @Field("FaceId")String FaceId,
                          @Field("userEmail")String userEmail,
                          @Field("TwitterId")String TwitterId);

}
