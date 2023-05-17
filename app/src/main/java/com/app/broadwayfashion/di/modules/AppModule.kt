package com.app.broadwayfashion.di.modules

import android.content.Context
import com.app.broadwayfashion.utils.AppSharePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): AppSharePreferences =
        AppSharePreferences(context)
}
