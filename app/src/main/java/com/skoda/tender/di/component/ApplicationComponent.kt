package com.skoda.tender.di.component

import android.content.Context
import android.content.SharedPreferences
import com.skoda.tender.App
import com.skoda.tender.di.module.ApplicationModule
import com.skoda.tender.di.module.DatabaseModule
import com.skoda.tender.ui.main.MainActivityViewModel

import com.skoda.tender.di.module.NetModule

import dagger.Component
import javax.inject.Singleton


@Singleton

@Component(modules = arrayOf(ApplicationModule::class,NetModule::class,DatabaseModule::class))


interface ApplicationComponent {
    fun app(): App
    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(mainActivityViewModel: MainActivityViewModel)
}
