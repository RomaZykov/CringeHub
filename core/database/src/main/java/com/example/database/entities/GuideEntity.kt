package com.example.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guides_table")
data class GuideEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "latestModified") val latestModified: Long,
    @ColumnInfo(name = "isDraft") val isDraft: Boolean
//    val isFree: Boolean,
//    val images: List<String>
)
