package com.app.broadwayfashion.network


import android.util.Log
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.PARENT_AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


class AdminAuthInterceptor @Inject constructor(private val preferences: AppSharePreferences) :
    Interceptor {


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

        var authToken = preferences.getString(PARENT_AUTH_TOKEN, "")
        if (authToken.isEmpty())
            authToken =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL3d3dy5icm9hZHdheWZhc2hpb24uY2EiLCJpYXQiOjE2ODA2MzgxNjEsIm5iZiI6MTY4MDYzODE2MSwiZXhwIjoxNjgxMjQyOTYxLCJkYXRhIjp7InVzZXIiOnsiaWQiOiI2NzEifX19.2EVPg1sxC0gRwbbgW7nArN0-8Nw4sy9a6Ux4PYCd_Vg"

        builder.add("Authorization", "Bearer $authToken")

        return builder.build()
    }

}