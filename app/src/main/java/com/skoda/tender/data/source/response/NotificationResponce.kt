package com.skoda.tender.data.source.response

import com.google.gson.annotations.SerializedName

// Data class representing the response from a notification API
data class NotificationResponce (

  // Message indicating the result of the notification request
  @SerializedName("message") var message: String? = null,

  // Response code indicating the status of the notification request
  @SerializedName("code") var code: Int? = null

)
