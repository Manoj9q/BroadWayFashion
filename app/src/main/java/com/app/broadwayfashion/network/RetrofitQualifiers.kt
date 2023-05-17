package com.app.broadwayfashion.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdminRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserJwtRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdminInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserInterceptor


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdminOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserJwtOkhttpClient
