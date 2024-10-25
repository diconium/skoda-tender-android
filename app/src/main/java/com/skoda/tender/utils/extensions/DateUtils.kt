package com.skoda.tender.utils.extensions

import android.content.Context
import com.skoda.tender.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {
    // Caching the SimpleDateFormat instance for better performance
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    /**
     * Formats the date based on whether it is expired or not.
     *
     * @param date The date to compare against the current date.
     * @param isExpired A Boolean indicating whether the date is in the past.
     * @param context The application context for resource access.
     * @return A formatted string indicating expiration status.
     */
    fun subDateFormatter(date: Date, isExpired: Boolean, context: Context): String {
        // Get the current date
        val currentDate = Date()

        // Calculate the difference in milliseconds based on whether the date is expired
        val diffInMillisec: Long = if (isExpired) {
            currentDate.time - date.time  // Calculate the difference for expired dates
        } else {
            date.time - currentDate.time    // Calculate the difference for future dates
        }

        // Convert the difference from milliseconds to days
        val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)

        // Return a formatted string based on the number of days difference
        return when {
            isExpired -> context.getString(R.string.expired_days_ago, diffInDays)  // Format for expired dates
            diffInDays <= 30 -> context.getString(R.string.expires_in_day, diffInDays)  // Format for dates expiring in <= 30 days
            else -> context.getString(R.string.expires_on_date, dateFormatter.format(date))  // Format for dates that expire later
        }
    }
}
