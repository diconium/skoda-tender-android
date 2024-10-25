package com.skoda.tender.di.component.di

import com.skoda.tender.data.datasource.SubscriptionsDataSource
import com.skoda.tender.data.datasourceimpl.SubscriptionsDataSourceImpl
import com.skoda.tender.data.source.remote.SubscriptionsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Dagger module responsible for providing data source implementations
@Module
class DataSourceModule {

    // Provides a singleton instance of SubscriptionsDataSource
    @Singleton
    @Provides
    fun provideSubscriptionsDataSource(subscriptionsApi: SubscriptionsApi): SubscriptionsDataSource {
        // Create and return an instance of SubscriptionsDataSourceImpl
        return SubscriptionsDataSourceImpl(subscriptionsApi)
    }
}
