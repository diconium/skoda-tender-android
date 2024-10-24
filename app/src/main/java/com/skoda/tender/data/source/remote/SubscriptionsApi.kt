package com.skoda.tender.data.source.remote


import com.skoda.tender.data.source.response.VehicleResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface defining the API endpoints for subscriptions-related operations.
 */
interface SubscriptionsApi {

    /**
     * Retrieves the subscription status for a given Vehicle Identification Number (VIN).
     *
     * This suspending function makes a GET request to the specified endpoint
     * and returns the response containing the vehicle's subscription status.
     *
     * @param vin The Vehicle Identification Number used to fetch the subscription status.
     * @return A [Response] containing [VehicleResponse] with the subscription data.
     */
    @GET("subscriptions/{vin}/status")
    suspend fun getSubscriptions(@Path("vin") vin: String): Response<VehicleResponse>
}
