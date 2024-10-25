package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing the payload of a notification
data class NotificationPayload(

  // Unique identifier for the notification
  @SerializedName("id") var id: Int? = null,

  // Vehicle Identification Number (VIN) associated with the notification
  @SerializedName("vin") var vin: String? = null,

  // Title of the notification
  @SerializedName("title") var title: String? = null,

  // Detailed message content of the notification
  @SerializedName("message") var message: String? = null

)
