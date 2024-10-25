package com.skoda.tender.utils.extensions

import android.content.Context
import com.skoda.tender.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {
    // Caching the SimpleDateFormat instance for better performance
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    /**
     * Formats the date based on whether it is expired or not.
     *
     * @param date The date to compare against the current date.
     * @param isExpired A Boolean indicating whether the date is in the past.
     * @param context The application context for resource access.
     * @return A formatted string indicating expiration status.
     */
    fun subDateFormatter(date: Date, isExpired: Boolean, context: Context): String {



        val format = dateFormatter.format(date)

        // Return a formatted string based on the number of days difference
        return when {
            isExpired -> context.getString(R.string.expires_on_date, format)  // Format for expired dates
            else -> {
                context.getString(R.string.expired_days_ago, format)
            }  // Format for dates that expire later
        }
    }
}
