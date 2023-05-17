package com.app.broadwayfashion.network

import com.app.broadwayfashion.model.admintoken.AdminTokenResponse
import com.app.broadwayfashion.model.cart.AddToCartResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JwtApiService {

    @POST("jwt-auth/v1/token")
    suspend fun getAdminToken(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<AdminTokenResponse>

    @POST("cocart/v2/cart/add-item")
    suspend fun addToCart(
        @Query("cart_key") cartKey: String,
        @Query("id") id: String,
        @Query("quantity") quantity: String,
        @Query("productId") productId: String,
    ): Response<AddToCartResponse>


    @POST("cocart/v2/cart/add-item")
    suspend fun addToCartByUser(
        @Query("username") userName: String,
        @Query("password") password: String,
        @Query("id") id: String,
        @Query("quantity") quantity: String,
        @Query("productId") productId: String,
    ): Response<AddToCartResponse>

    @GET("cocart/v2/cart")
    suspend fun getCartInfo(
        @Query("cart_key") cartKey: String,
    ): Response<AddToCartResponse>

    @GET("cocart/v2/cart")
    suspend fun getCartInfoOfUser(
        @Query("username") userName: String,
        @Query("password") password: String,
    ): Response<AddToCartResponse>

    @DELETE("cocart/v2/cart/item/{itemKey}")
    suspend fun removeItemCartByUser(
        @Path("itemKey") key: String,
        @Query("username") userName: String,
        @Query("password") password: String
    ): Response<AddToCartResponse>

    @DELETE("cocart/v2/cart/item/{itemKey}")
    suspend fun removeItemCart(
        @Path("itemKey") key: String,
        @Query("cart_key") cartKey: String
    ): Response<AddToCartResponse>

    @POST("cocart/v2/cart/item/{itemKey}")
    suspend fun updateCartQuantity(
        @Path("itemKey") key: String,
        @Query("quantity") quantity: String,
        @Query("cart_key") cartKey: String
    ): Response<AddToCartResponse>

    @POST("cocart/v2/cart/item/{itemKey}")
    suspend fun updateCartQuantityByUser(
        @Path("itemKey") key: String,
        @Query("quantity") quantity: String,
        @Query("username") userName: String,
        @Query("password") password: String
    ): Response<AddToCartResponse>

    @POST("cocart/v2/cart/clear")
    suspend fun clearAllCart(
        @Query("username") userName: String,
        @Query("password") password: String
    ): Response<AddToCartResponse>
}