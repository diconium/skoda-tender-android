package com.skoda.tender.di.module

import android.os.Environment
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skoda.tender.data.source.remote.SubscriptionsApi
import com.skoda.tender.data.source.remote.TelematicsApiService
import com.skoda.tender.data.source.remote.config.APIConfig
import com.skoda.tender.utils.convertor.DateDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLSession

// Dagger module responsible for providing network-related dependencies
@Module
class NetModule {

    // Provides a singleton OkHttpClient instance with caching enabled
    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(): OkHttpClient {
        // Create a cache for network responses, limiting to 10MB
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES) // Set read timeout
            .writeTimeout(1, TimeUnit.MINUTES) // Set write timeout
            .cache(cache) // Enable caching
            .build()
    }

    // Provides a singleton OkHttpClient instance without caching
    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(): OkHttpClient {
        // Set up logging interceptor for HTTP requests
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Log body of requests
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES) // Set read timeout
            .writeTimeout(1, TimeUnit.MINUTES) // Set write timeout
            .addInterceptor(interceptor) // Add logging interceptor
            .hostnameVerifier({ hostname: String?, session: SSLSession? -> true }) // Disable hostname verification for SSL
            .build()
    }

    // Provides a singleton Gson instance for JSON serialization/deserialization
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // Configure field naming policy
        gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer()) // Register custom date deserializer
        return gsonBuilder.create() // Create and return Gson instance
    }

    // Provides a Retrofit.Builder instance configured with the given Gson and non-cached OkHttpClient
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Named("non_cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client) // Set the OkHttpClient
            .addConverterFactory(GsonConverterFactory.create(gson)) // Set Gson converter for JSON
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Set RxJava2 call adapter
    }

    /**
     * Provides the TelematicsApiService for telematics-related API calls
     */
    @Provides
    @Singleton
    fun provideTelematicsService(builder: Retrofit.Builder): TelematicsApiService {
        return builder.baseUrl(APIConfig.API_URL) // Set base URL for the API
            .build() // Build the Retrofit instance
            .create(TelematicsApiService::class.java) // Create and return the API service
    }

    /**
     * Provides the SubscriptionsApi for subscription-related API calls
     */
    @Provides
    @Singleton
    fun provideSubscriptionsService(builder: Retrofit.Builder): SubscriptionsApi {
        return builder.baseUrl(APIConfig.API_URL) // Set base URL for the API
            .build() // Build the Retrofit instance
            .create(SubscriptionsApi::class.java) // Create and return the API service
    }
}
