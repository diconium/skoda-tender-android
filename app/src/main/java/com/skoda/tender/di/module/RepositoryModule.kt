package com.skoda.tender.di.component.di


import com.skoda.tender.data.datasource.SubscriptionsDataSource
import com.skoda.tender.domain.repository.SubscriptionsRepository
import com.skoda.tender.domain.repositoryimpl.SubscriptionsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesSubscriptionsRepository(subscriptionsDataSource: SubscriptionsDataSource): SubscriptionsRepository {
        return SubscriptionsRepositoryImpl(subscriptionsDataSource)
    }
}