package com.skoda.tender.di.component.di


import com.skoda.tender.data.datasource.SubscriptionsDataSource
import com.skoda.tender.data.datasourceimpl.SubscriptionsDataSourceImpl
import com.skoda.tender.data.source.remote.SubscriptionsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideSubscriptionsDataSource(subscriptionsApi: SubscriptionsApi): SubscriptionsDataSource {
        return SubscriptionsDataSourceImpl(subscriptionsApi)
    }
}