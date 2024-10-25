package com.skoda.tender.di.component.di

import com.skoda.tender.domain.repository.SubscriptionsRepository
import com.skoda.tender.domain.usecase.SubscriptionsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Dagger module responsible for providing use case-related dependencies
@Module
class UseCaseModule {

    /**
     * Provides a singleton instance of SubscriptionsUseCase.
     *
     * @param subscriptionsRepository The repository used by the use case.
     * @return An instance of SubscriptionsUseCase.
     */
    @Singleton
    @Provides
    fun provideSubscriptionsUseCase(subscriptionsRepository: SubscriptionsRepository): SubscriptionsUseCase {
        // Create and return an instance of SubscriptionsUseCase with the provided repository
        return SubscriptionsUseCase(subscriptionsRepository)
    }
}
