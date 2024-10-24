package com.skoda.tender.domain.repository


import com.skoda.tender.data.source.response.ApiResult
import com.skoda.tender.data.source.response.VehicleResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for fetching subscriptions-related data.
 *
 * This interface defines the contract for obtaining story lists from a data source.
 */
interface SubscriptionsRepository {

    /**
     * Fetches the list of subscriptions as a [Flow] of [ApiResult].
     *
     * This function is a suspending function that returns a flow of the API result,
     * which may contain either a successful response or an error.
     *
     * @return A [Flow] emitting [ApiResult] of [VehicleResponse].
     */
    suspend fun fetchStoryList(): Flow<ApiResult<VehicleResponse>>
}
