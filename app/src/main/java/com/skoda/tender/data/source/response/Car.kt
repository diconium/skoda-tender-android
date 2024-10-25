package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing a car entity with various properties
data class Car(

    // Vehicle Identification Number
    @SerializedName("vin") var vin: String? = null,

    // Brand of the car (e.g., Skoda)
    @SerializedName("brand") var brand: String? = null,

    // Model of the car (e.g., Octavia)
    @SerializedName("model") var model: String? = null,

    // Manufacturing year of the car
    @SerializedName("year") var year: Int? = null

)
