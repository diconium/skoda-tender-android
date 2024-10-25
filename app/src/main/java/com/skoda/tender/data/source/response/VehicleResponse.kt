package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing the response for vehicle-related data
data class VehicleResponse(

    // User associated with the vehicle
    @SerializedName("user") var user: User? = User(),

    // Car details
    @SerializedName("car") var car: Car? = Car(),

    // List of subscriptions related to the vehicle
    @SerializedName("subscriptions") var subscriptions: ArrayList<Subscriptions> = arrayListOf()

)
