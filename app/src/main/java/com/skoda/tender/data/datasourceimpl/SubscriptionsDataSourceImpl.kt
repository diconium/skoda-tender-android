package com.skoda.tender.data.datasourceimpl


import com.skoda.tender.data.datasource.SubscriptionsDataSource
import com.skoda.tender.data.source.remote.SubscriptionsApi
import com.skoda.tender.data.source.response.VehicleResponse
import retrofit2.Response

/**
 * Implementation of the [SubscriptionsDataSource] interface that interacts with the API
 * to fetch subscriptions-related data.
 *
 * @property api The API service used to retrieve story lists.
 */
class SubscriptionsDataSourceImpl(private var api: SubscriptionsApi) : SubscriptionsDataSource {

    /**
     * Retrieves the list of subscriptions based on the provided VIN.
     *
     * This function calls the API to get subscriptions data associated with the given VIN.
     *
     * @param vin The Vehicle Identification Number used to fetch story lists.
     * @return A [Response] containing [VehicleResponse] from the API.
     */
    override suspend fun getStoryLists(vin: String): Response<VehicleResponse> {
        return api.getSubscriptions(vin)
    }
}
