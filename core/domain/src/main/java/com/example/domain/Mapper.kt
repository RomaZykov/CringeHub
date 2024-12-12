package com.example.domain

interface Mapper<T> {
    fun mappedValue(): T
}