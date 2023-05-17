package com.app.broadwayfashion.network


import android.util.Log
import com.app.broadwayfashion.utils.AppSharePreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class JwtAuthInterceptor @Inject constructor(private val preferences: AppSharePreferences) :
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

        /* var authToken = preferences.getString(PARENT_AUTH_TOKEN, "")
         if(authToken.isEmpty())*/
/*        var authToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL3d3dy5icm9hZHdheWZhc2hpb24uY2EiLCJpYXQiOjE2ODA0NjI2ODcsIm5iZiI6MTY4MDQ2MjY4NywiZXhwIjoxNjgxMDY3NDg3LCJkYXRhIjp7InVzZXIiOnsiaWQiOiI2NzEifX19.2Mv77rY3roWcbdybmlnykuU7lrkGZqJPKGRt2oRBBOs"

        builder.add("Authorization", "Bearer $authToken")*/

        return builder.build()
    }

}