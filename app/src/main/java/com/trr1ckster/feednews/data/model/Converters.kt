package com.trr1ckster.feednews.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun sourceToString(source: Source): String = Gson().toJson(source)

    @TypeConverter
    fun stringToSource(currentForecast: String): Source =
        Gson().fromJson(currentForecast, Source::class.java)

}