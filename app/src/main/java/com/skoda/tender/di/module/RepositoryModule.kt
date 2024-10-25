package com.skoda.tender.di.component.di

import com.skoda.tender.data.datasource.SubscriptionsDataSource
import com.skoda.tender.domain.repository.SubscriptionsRepository
import com.skoda.tender.domain.repositoryimpl.SubscriptionsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Dagger module responsible for providing repository-related dependencies
@Module
class RepositoryModule {

    /**
     * Provides a singleton instance of SubscriptionsRepository.
     *
     * @param subscriptionsDataSource The data source to be used by the repository.
     * @return An instance of SubscriptionsRepository.
     */
    @Singleton
    @Provides
    fun providesSubscriptionsRepository(subscriptionsDataSource: SubscriptionsDataSource): SubscriptionsRepository {
        // Create and return an instance of SubscriptionsRepositoryImpl
        return SubscriptionsRepositoryImpl(subscriptionsDataSource)
    }
}
