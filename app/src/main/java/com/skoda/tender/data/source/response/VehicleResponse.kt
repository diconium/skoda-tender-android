package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName


data class VehicleResponse(

    @SerializedName("user") var user: User? = User(),
    @SerializedName("car") var car: Car? = Car(),
    @SerializedName("subscriptions") var subscriptions: ArrayList<Subscriptions> = arrayListOf()

)