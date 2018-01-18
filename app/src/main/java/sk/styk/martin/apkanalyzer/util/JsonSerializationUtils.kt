package sk.styk.martin.apkanalyzer.util

import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.GsonBuilder

/**
 * @author Martin Styk
 * @version 06.11.2017.
 */
class JsonSerializationUtils {

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm a z").create()

    fun serialize(`object`: Any): String {
        return gson.toJson(`object`)
    }
}
