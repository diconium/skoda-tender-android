package com.skoda.tender.data.source.response

// Sealed class to represent the result of an API call
sealed class ApiResult<T>(
    val data: T? = null,             // Holds the response data if successful
    val message: String? = null,     // Holds an error message, if applicable
    val responseCode: String? = null  // Holds the HTTP response code or other relevant codes
) {

    // Represents a successful API response with data
    class Success<T>(data: T) : ApiResult<T>(data)

    // Represents an error response with an optional data payload
    class Error<T>(message: String, data: T? = null) : ApiResult<T>(data, message)

    // Represents the loading state of an API call
    class Loading<T> : ApiResult<T>()

    // Represents a state where there is no result to return
    class Nothing<T> : ApiResult<T>()
}
