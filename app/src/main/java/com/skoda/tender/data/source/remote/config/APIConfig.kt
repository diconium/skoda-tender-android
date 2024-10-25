package com.skoda.tender.data.source.remote.config

/**
 * Object containing API configuration constants.
 *
 * This object holds the base URL for the API and a sample Vehicle Identification Number (VIN).
 */
object APIConfig {
    /**
     * The base URL for the API.
     */
    const val API_URL: String = "http://skoda-backend-dev-lb-872235717.eu-central-1.elb.amazonaws.com/"

    /**
     * A sample Vehicle Identification Number (VIN) used for testing or as a default value.
     */
    const val VIN_NO: String = "TMBJR0NX4RY183174"
}

