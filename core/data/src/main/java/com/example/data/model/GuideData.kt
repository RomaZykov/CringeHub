package com.example.data.model

import com.example.database.entities.GuideLocal
import com.example.domain.Mapper

data class GuideData(
    val id: Int,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val images: List<String>
)  : Mapper<GuideLocal> {
    override fun mappedValue(): GuideLocal {
        return GuideLocal(this.id, this.title)
    }
}
