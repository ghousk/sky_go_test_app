package com.innovativequest.sky_go_test_app.model

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromStringToDataListItemResponse(data: String?): List<DataListItem>? {

        var gson = Gson()
        if (data == null) {
            return ArrayList<DataListItem>()
        }

        val objectType = object : TypeToken<List<DataListItem>>() {
        }.type

        return gson.fromJson(data, objectType)
    }

    @TypeConverter
    @JvmStatic
    fun fromDataListItemResponsesToString(someObject: List<DataListItem>?): String {
        var gson = Gson()

        return gson.toJson(someObject)
    }
}
