package com.skoda.tender.utils.extensions

import android.content.Context
import com.skoda.tender.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {

    fun subDateFormater(date: Date, isExpired: Boolean, context: Context): String {

        if (isExpired) {
            val diffInMillisec: Long = Date().time - date.time
            val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
            return context.getString(R.string.expired_days_ago, diffInDays)
        } else {
            val diffInMillisec: Long = date.time - Date().time
            val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
            return if (diffInDays <= 30)
                context.getString(R.string.expires_in_day, diffInDays)
            else
                context.getString(R.string.expires_on_date, dateFormater(date))
        }
    }

    private fun dateFormater(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = formatter.format(date)
        return formattedDate;
    }
}