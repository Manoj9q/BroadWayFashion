package com.app.broadwayfashion.di.modules


import com.app.broadwayfashion.BuildConfig
import com.app.broadwayfashion.network.AdminAuthInterceptor
import com.app.broadwayfashion.network.AdminOkhttpClient
import com.app.broadwayfashion.network.AdminRetrofit
import com.app.broadwayfashion.network.ApiService
import com.app.broadwayfashion.network.AuthInterceptor
import com.app.broadwayfashion.network.JwtApiService
import com.app.broadwayfashion.network.JwtAuthInterceptor
import com.app.broadwayfashion.network.ParentApiService
import com.app.broadwayfashion.network.UserJwtOkhttpClient
import com.app.broadwayfashion.network.UserJwtRetrofit
import com.app.broadwayfashion.network.UserOkhttpClient
import com.app.broadwayfashion.network.UserRetrofit
import com.app.broadwayfashion.utils.AppSharePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@DelicateCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @UserRetrofit
    @Provides
    fun provideUserAuthInterceptor(preferences: AppSharePreferences): AuthInterceptor =
        AuthInterceptor(preferences)

    @AdminRetrofit
    @Provides
    fun provideAuthInterceptor(preferences: AppSharePreferences): AdminAuthInterceptor =
        AdminAuthInterceptor(preferences)

    @UserJwtRetrofit
    @Provides
    fun provideJwtAuthInterceptor(preferences: AppSharePreferences): JwtAuthInterceptor =
        JwtAuthInterceptor(preferences)


    @Provides
    @UserJwtOkhttpClient
    fun provideOkHttpJwtClient(authInterceptor: JwtAuthInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @AdminOkhttpClient
    fun provideOkHttpClient(authInterceptor: AdminAuthInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @UserOkhttpClient
    fun provideOkHttpUserClient(authInterceptor: AuthInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder().callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


/*    @Provides
    fun provideAdminRetrofit(@AdminRetrofit okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    fun provideUserRetrofit(@UserRetrofit okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()*/

    @Provides
    fun getUserApiService(
        @UserOkhttpClient client: OkHttpClient
    ): ApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build().create(ApiService::class.java)

    @Provides
    fun getAdminApiService(
        @AdminOkhttpClient client: OkHttpClient
    ): ParentApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build().create(ParentApiService::class.java)


    @Provides
    fun getJwtApiService(
        @UserJwtOkhttpClient client: OkHttpClient
    ): JwtApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build().create(JwtApiService::class.java)


/*    @Provides
    fun provideApiService(@UserRetrofit retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Provides
    fun provideParentApiService(@AdminRetrofit retrofit: Retrofit): ParentApiService =
        retrofit.create(ParentApiService::class.java)*/


}