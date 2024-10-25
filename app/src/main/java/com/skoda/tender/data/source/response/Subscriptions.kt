package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName
import java.util.Date

// Data class representing a subscription
data class Subscriptions(

    // Unique identifier for the subscription
    @SerializedName("id") var id: Int? = null,

    // Name of the subscription
    @SerializedName("name") var name: String? = null,

    // Description of the subscription
    @SerializedName("description") var description: String? = null,

    // Link to an image representing the subscription
    @SerializedName("imageLink") var imageLink: String? = null,

    // Length of the subscription in days/months (depends on the context)
    @SerializedName("length") var length: Int? = null,

    // Price of the subscription
    @SerializedName("price") var price: Double? = null,

    // List of included services for this subscription
    @SerializedName("includedServices") var includedServices: ArrayList<IncludedServices> = arrayListOf(),

    // Current status of the subscription (e.g., activated, inactive)
    @SerializedName("status") var status: String? = null,

    // Start date of the subscription
    @SerializedName("startDate") var startDate: Date? = null,

    // End date of the subscription
    @SerializedName("endDate") var endDate: Date? = null

)

// Object representing possible statuses for subscriptions
object SubscriptionStatus {
    const val ACTIVATED = "Activated" // Subscription is active
    const val INACTIVE = "Inactive"   // Subscription is not active
}
