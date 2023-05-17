package com.app.broadwayfashion.network

import android.content.Context
import android.util.Log
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.USER_AUTH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


class AuthInterceptor @Inject constructor(private val preferences: AppSharePreferences) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .headers(getJsonHeader())
            .build()
        val response = chain.proceed(request)
        Log.e("AUTH INTERCEPTOR", "${response.code}")

        return response
    }

    private fun getJsonHeader(): okhttp3.Headers {
        val builder = okhttp3.Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("Accept", "application/json")
        //builder.add("Content-Type", "application/x-www-form-urlencoded")
        var authToken = preferences.getString(USER_AUTH_TOKEN, "")

        if (authToken.isNotBlank()) {
            builder.add("Authorization", "Bearer $authToken")
        }
        return builder.build()
    }

}