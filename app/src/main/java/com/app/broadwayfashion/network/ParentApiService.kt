package com.app.broadwayfashion.network

import com.app.broadwayfashion.model.BillingUpdateRequest
import com.app.broadwayfashion.model.ShippingUpdateRequest
import com.app.broadwayfashion.model.UserSignupResponse
import com.app.broadwayfashion.model.admintoken.AdminTokenResponse
import com.app.broadwayfashion.model.customer.CustomerDataResponse
import com.app.broadwayfashion.model.forgotpassword.ForgotPasswordResponse
import com.app.broadwayfashion.model.order.OrderResponse
import com.app.broadwayfashion.model.order.OrderResponseItem
import com.app.broadwayfashion.model.orderrequest.OrderRequestModel
import com.app.broadwayfashion.model.product.CategoryListResponse
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.model.product.ProductListResponse
import com.app.broadwayfashion.model.request.SignupRequest
import com.app.broadwayfashion.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ParentApiService {

    @POST("jwt-auth/v1/token")
    suspend fun getAdminToken(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<AdminTokenResponse>

    @GET("wc/v3/products")
    suspend fun getAllProduct(
        @Query("page") page: String,
        @Query("per_page") PerPage: String
    ): Response<ProductListResponse>

    @GET("wc/v3/products")
    suspend fun getProductListByCategory(
        @Query("category") category: Int,
        @Query("page") page: String,
        @Query("per_page") PerPage: String
    ): Response<ProductListResponse>


    @GET("wc/v3/products")
    suspend fun getProductListByCategoryMap(
        @QueryMap map: LinkedHashMap<String, String>
    ): Response<ProductListResponse>

    @GET("wc/v3/products")
    suspend fun getProductListByCategoryMap2(
        @Query("attribute") sizeType: String,
        @Query("attribute_term") size: String,
        @Query("attribute") colorType: String,
        @Query("attribute_term") color: String,
        @Query("attribute") brandType: String,
        @Query("attribute_term") brand: String,
        @Query("orderby") orderby: String,
        @Query("order") order: String,
        @Query("category") category: String,
        @Query("page") page: String,
        @Query("per_page") PerPage: String
    ): Response<ProductListResponse>

    @GET("wc/v3/products")
    suspend fun getProductListByText(
        @Query("search") text: String,
        @Query("page") page: String,
        @Query("per_page") PerPage: String
    ): Response<ProductListResponse>

    @GET("wc/v3/products/categories")
    suspend fun getSubCategory(
        @Query("parent") category: String
    ): Response<CategoryListResponse>

    @GET("wc/v3/products/{productId}")
    suspend fun getProductDetailById(
        @Path("productId") id: String
    ): Response<ProductItem>

    @GET("wc/v2/products")
    suspend fun getSimilarProduct(
        @Query("include") include: String,
    ): Response<ProductListResponse>

    @POST("jwt-auth/v1/token")
    suspend fun userLogin(
        @Body hashMap: HashMap<String, String>
    ): Response<LoginResponse>


    @GET("wc/v3/customers/{id}")
    suspend fun getUserDetail(
        @Path("id") id: String
    ): Response<CustomerDataResponse>

    @POST("wp/v2/users")
    suspend fun userSignUp(
        @Body request: SignupRequest
    ): Response<UserSignupResponse>

    @POST("bdpwr/v1/reset-password")
    suspend fun forgotPasswordOtpRequest(
        @Query("email") email: String,
    ): Response<ForgotPasswordResponse>

    @POST("bdpwr/v1/set-password")
    suspend fun setNewPassword(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("code") code: String
    ): Response<ForgotPasswordResponse>

    @PUT("wc/v3/customers/{customerId}")
    suspend fun updateProfile(
        @Path("customerId") customerId: String,
        @Query("first_name") fName: String,
        @Query("last_name") lName: String,
        @Query("email") Email: String
    ): Response<CustomerDataResponse>

    @PUT("wc/v3/customers/{customerId}")
    suspend fun updateUserAddress(
        @Path("customerId") customerId: String,
        @Body request: ShippingUpdateRequest
    ): Response<CustomerDataResponse>

    @PUT("wc/v3/customers/{customerId}")
    suspend fun updateUserBilling(
        @Path("customerId") customerId: String,
        @Body request: BillingUpdateRequest
    ): Response<CustomerDataResponse>

    @GET("wc/v3/Orders")
    suspend fun getOrderList(
        @Query("customer") customer: String,
        @Query("page") page: String,
        @Query("per_page") PerPage: String
    ): Response<OrderResponse>

    @POST("wc/v3/Orders")
    suspend fun createOrder(
        @Body request: OrderRequestModel
    ): Response<OrderResponseItem>
}