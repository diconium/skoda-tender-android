package com.skoda.tender.data.source.remote

import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface defining the API endpoints for telematics-related operations.
 */
interface TelematicsApiService {

    /**
     * Retrieves vehicle data.
     *
     * This function makes a GET request to the specified endpoint
     * and returns the response containing vehicle data as a JSON string.
     *
     * @return A [Response] containing the vehicle data in JSON format.
     */
    @GET("/api/vehicle")
    fun getVehicleData(): retrofit2.Response<String>

    /**
     * Sends the vehicle speed to the server.
     *
     * This function makes a POST request to update the vehicle's speed.
     * The speed value should be provided as a string.
     *
     * @param value The speed of the vehicle to be sent to the server.
     * @return A [Response] indicating the result of the update operation.
     */
    @POST("/api/update")
    fun sendVehicleSpeed(value: String): retrofit2.Response<String>
}
