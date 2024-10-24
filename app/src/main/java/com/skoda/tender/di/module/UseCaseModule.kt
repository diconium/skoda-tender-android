package com.skoda.tender.di.component.di

import com.skoda.tender.domain.repository.SubscriptionsRepository
import com.skoda.tender.domain.usecase.SubscriptionsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSubscriptionsUseCase(subscriptionsRepository: SubscriptionsRepository): SubscriptionsUseCase {
        return SubscriptionsUseCase(subscriptionsRepository)
    }
}