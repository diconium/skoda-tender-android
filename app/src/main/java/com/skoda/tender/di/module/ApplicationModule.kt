package com.skoda.tender.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.skoda.tender.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(var mainApplication: MainApplication) {


    @Provides
    @Singleton
    fun provideApp(): MainApplication = mainApplication

    @Provides
    @Singleton
    fun provideContext(): Context = mainApplication.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainApplication)
}
