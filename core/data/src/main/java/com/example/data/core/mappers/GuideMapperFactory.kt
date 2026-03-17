package com.example.data.core.mappers

import com.example.data.model.GuideData
import com.example.database.entities.GuideEntity
import com.example.domain.model.GuideDomain
import com.example.network.model.GuideNetwork
import javax.inject.Inject

interface GuideMapperFactory {

    fun <T> mapToDomain(input: T): GuideDomain

    fun <T> mapToData(input: T): GuideData

    fun <T> mapToNetwork(input: T): GuideNetwork

    fun <T> mapToEntity(input: T): GuideEntity

    class Base @Inject constructor() : GuideMapperFactory {
        override fun <T> mapToNetwork(input: T): GuideNetwork {
            return when (input) {
                is GuideEntity -> {
                    GuideNetwork(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        isFree = false,
                        latestModified = input.latestModified,
                        images = emptyList()
                    )
                }

                is GuideData -> {
                    GuideNetwork(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        isFree = false,
                        latestModified = input.latestModified,
                        images = emptyList()
                    )
                }

                else -> throw IllegalArgumentException("Unknown guide specific input type: $input")
            }
        }

        override fun <T> mapToEntity(input: T): GuideEntity {
            return when (input) {
                is GuideNetwork -> {
                    GuideEntity(
                        id = input.id ?: "",
                        title = input.title ?: "",
                        content = input.content ?: "",
                        isDraft = input.isDraft ?: true,
                        latestModified = input.latestModified ?: -1L
                    )
                }

                is GuideData -> {
                    GuideEntity(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        latestModified = input.latestModified
                    )
                }

                else -> throw IllegalArgumentException("Unknown guide specific input type: $input")
            }
        }

        override fun <T> mapToData(input: T): GuideData {
            return when (input) {
                is GuideEntity -> {
                    GuideData(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        isFree = false,
                        latestModified = input.latestModified,
                        images = emptyList()
                    )
                }

                is GuideNetwork -> {
                    GuideData(
                        id = input.id ?: "",
                        title = input.title ?: "",
                        content = input.content ?: "",
                        isDraft = input.isDraft ?: true,
                        isFree = false,
                        latestModified = input.latestModified ?: -1L,
                        images = emptyList()
                    )
                }

                else -> {
                    throw IllegalArgumentException("Unknown guide specific input type: $input")
                }
            }
        }

        override fun <T> mapToDomain(input: T): GuideDomain {
            return when (input) {
                is GuideEntity -> {
                    GuideDomain(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        isFree = false,
                        images = emptyList()
                    )
                }

                is GuideNetwork -> {
                    GuideDomain(
                        id = input.id ?: "",
                        title = input.title ?: "",
                        content = input.content ?: "",
                        isDraft = input.isDraft ?: true,
                        isFree = input.isFree ?: false,
                        images = emptyList()
                    )
                }

                is GuideData -> {
                    GuideDomain(
                        id = input.id,
                        title = input.title,
                        content = input.content,
                        isDraft = input.isDraft,
                        isFree = input.isFree,
                        images = emptyList()
                    )
                }

                else -> {
                    throw IllegalArgumentException("Unknown guide specific input type: $input")
                }
            }
        }
    }
}
