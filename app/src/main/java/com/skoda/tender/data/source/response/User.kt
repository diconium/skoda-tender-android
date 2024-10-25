package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing a user
data class User(

    // Unique identifier for the user
    @SerializedName("id") var id: Int? = null,

    // Email address of the user
    @SerializedName("email") var email: String? = null,

    // Name of the user
    @SerializedName("name") var name: String? = null

)
