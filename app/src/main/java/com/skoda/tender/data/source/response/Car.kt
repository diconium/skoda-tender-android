package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName


data class Car(

    @SerializedName("vin") var vin: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("model") var model: String? = null,
    @SerializedName("year") var year: Int? = null

)