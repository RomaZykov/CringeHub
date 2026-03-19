package com.example.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "guides_table")
data class GuideEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") @TypeConverters(GsonMapConverter::class) val content: Map<Int, String>,
    @ColumnInfo(name = "latestModified") val latestModified: Long,
    @ColumnInfo(name = "isDraft") val isDraft: Boolean
//    val isFree: Boolean,
//    val images: List<String>
)

// TODO: Why not interface?
internal class GsonMapConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: Map<Int, String>?): String? {
        return map?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toMap(json: String?): Map<Int, String>? {
        val mapType = object : TypeToken<Map<Int, String>>() {}.type
        return json?.let { gson.fromJson(it, mapType) }
    }
}
