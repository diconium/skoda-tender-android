package com.skoda.tender.di.component


import android.content.Context
import com.skoda.tender.ui.viewmodel.ServiceViewModel
import com.skoda.tender.MainApplication
import com.skoda.tender.di.component.di.DataSourceModule
import com.skoda.tender.di.component.di.RepositoryModule
import com.skoda.tender.di.component.di.UseCaseModule
import com.skoda.tender.di.module.ApplicationModule
import com.skoda.tender.di.module.DatabaseModule
import com.skoda.tender.di.module.NetModule
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for dependency injection in the application.
 *
 * This component is responsible for providing application-level dependencies,
 * including modules for networking, database, use cases, data sources, and repositories.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetModule::class,
        DatabaseModule::class,
        UseCaseModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {

    /**
     * Provides the [MainApplication] instance.
     *
     * @return The application instance.
     */
    fun app(): MainApplication

    /**
     * Provides the application context.
     *
     * @return The application context.
     */
    fun context(): Context



    /**
     * Injects dependencies into the [ServiceViewModel].
     *
     * @param serviceViewModel The [ServiceViewModel] instance to inject dependencies into.
     */
    fun inject(serviceViewModel: ServiceViewModel)
}
