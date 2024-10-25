package com.skoda.tender.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.skoda.tender.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Dagger module for providing application-level dependencies
@Module
class ApplicationModule(var mainApplication: MainApplication) {

    // Provides the MainApplication instance
    @Provides
    @Singleton
    fun provideApp(): MainApplication = mainApplication

    // Provides the application context
    @Provides
    @Singleton
    fun provideContext(): Context = mainApplication.applicationContext

    // Provides the SharedPreferences instance for the application
    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainApplication)
}
