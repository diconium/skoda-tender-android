package com.skoda.tender.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.skoda.tender.MainApplication

open class BaseViewModel(val app: Application) : AndroidViewModel(app) {
    val mainApplication: MainApplication
        get() {
            return app as MainApplication
        }
}
