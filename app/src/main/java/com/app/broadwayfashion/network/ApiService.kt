package com.app.broadwayfashion.network

import com.app.broadwayfashion.model.CustomerIdResponse
import com.app.broadwayfashion.model.cart.AddToCartResponse
import com.app.broadwayfashion.model.customer.CustomerDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("testone/loggedinuser")
    suspend fun getUserId(
        @Body hashMap: HashMap<String, String>
    ): Response<CustomerIdResponse>


    @POST("cocart/v2/cart/add-item")
    suspend fun addToCart(
        @Query("id") id: String,
        @Query("quantity") quantity: String,
    ): Response<AddToCartResponse>

    @GET("cocart/v2/cart")
    suspend fun getCartInfo(): Response<AddToCartResponse>

    @GET("wc/v3/customers/{customerId}")
    suspend fun updateProfile(
        @Path("customerId") customerId: String,
        @Query("first_name") fName: String,
        @Query("last_name") lName: String,
        @Query("email") Email: String
    ): Response<CustomerDataResponse>
}