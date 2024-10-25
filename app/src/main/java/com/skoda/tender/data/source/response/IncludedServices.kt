package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing the included services for a car or product
data class IncludedServices(

    // Unique identifier for the service
    @SerializedName("id") var id: Int? = null,

    // Name of the service (e.g., Warranty, Insurance)
    @SerializedName("name") var name: String? = null,

    // Description of the service providing details
    @SerializedName("description") var description: String? = null,

    // Link to an image representing the service
    @SerializedName("imageLink") var imageLink: String? = null

)
