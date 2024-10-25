package com.skoda.tender.data.source.base


import android.util.Log
import com.skoda.tender.data.source.response.ApiResult
import retrofit2.Response



/**
 * Abstract class that provides a utility function for safe API calls.
 *
 * This class can be extended by repositories to handle API responses
 * and manage error handling in a consistent manner.
 */
abstract class BaseApiResponse {

    /**
     * Executes a safe API call and returns the result as an [ApiResult].
     *
     * This suspending function wraps the API call in a try-catch block,
     * checks for a successful response, and returns either the success result
     * or an error.
     *
     * @param apiCall A suspending function that performs the API call.
     * @return An [ApiResult] containing the response data if successful,
     *         or an error message if the call fails.
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            Log.i("TAG", "safeApiCall: calling  ")
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Log.i("TAG", "safeApiCall: success ")
                    return ApiResult.Success(it)
                }
            }
            Log.i("TAG", "safeApiCall:  error ${response.code()} ${response.message()}")

            return error("${response.code()} ${response.message()}")
        } catch (ex: Exception) {
            ex.printStackTrace()
            return error(ex.message ?: ex.toString())
        }
    }

    /**
     * Creates an error [ApiResult] with the provided error message.
     *
     * @param errorMessage The error message to be included in the [ApiResult].
     * @return An [ApiResult.Error] containing the error message.
     */
    private fun <T> error(errorMessage: String): ApiResult<T> = ApiResult.Error(errorMessage)
}
