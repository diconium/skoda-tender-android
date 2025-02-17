package com.skoda.tender

import com.skoda.tender.di.component.DaggerApplicationComponent
import com.skoda.tender.di.module.ApplicationModule

class MainApplication : android.app.Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

