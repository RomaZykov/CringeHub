package com.example.adminhome.model

data class CardItem(
    val title: String,
    val description: String = "",
    val image: Int,
    val onClicked: () -> Unit
)