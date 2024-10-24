package com.skoda.tender.di.module

import android.os.Environment
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Cache
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.Gson
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.skoda.tender.data.source.remote.SubscriptionsApi
import com.skoda.tender.data.source.remote.TelematicsApiService
import com.skoda.tender.data.source.remote.config.APIConfig
import com.skoda.tender.utils.convertor.DateDeserializer
import java.util.Date


@Module
class NetModule {
    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .cache(cache)
                .build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Named("non_cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    /**
     * Telematics related API service
     */
    @Provides
    @Singleton
   fun provideTelematicsService( builder:Retrofit.Builder) : TelematicsApiService {
        return builder.baseUrl(APIConfig.API_URL)
                .build()
                .create(TelematicsApiService::class.java)
    }

    /**
     * Sub related API service
     */
    @Provides
    @Singleton
   fun provideSubscriptionsService( builder:Retrofit.Builder) : SubscriptionsApi {
        return builder.baseUrl(APIConfig.API_URL)
                .build()
                .create(SubscriptionsApi::class.java)
    }
}