package com.skoda.tender.utils.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Custom deserializer for converting JSON date strings into [Date] objects.
 *
 * This class implements [JsonDeserializer] and is used by Gson to parse
 * date strings in the specified format into [Date] instances.
 */
class DateDeserializer : JsonDeserializer<Date> {

    /**
     * Deserializes a JSON element into a [Date] object.
     *
     * @param json The JSON element containing the date string.
     * @param typeOfT The type of the object to deserialize into.
     * @param context The context for deserialization.
     * @return The parsed [Date] object, or null if the JSON element is null.
     * @throws JsonParseException If the date string cannot be parsed.
     */
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date? {
        val dateString = json.asString
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        try {
            return sdf.parse(dateString)
        } catch (e: ParseException) {
            throw JsonParseException("Failed to parse Date: $dateString", e)
        }
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
    }
}
