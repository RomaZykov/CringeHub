package com.example.data

import com.example.data.model.GuideData
import com.example.database.entities.GuideEntity
import com.example.domain.model.Guide
import com.example.network.model.GuideNetwork
import javax.inject.Inject

interface MapperFactory {

    fun <T, R> mapBetweenGuidesSpecificType(input: T, output: Class<R>): R

    class Base @Inject constructor() : MapperFactory {
        override fun <T, R> mapBetweenGuidesSpecificType(input: T, output: Class<R>): R {
            when (input) {
                is GuideEntity -> {
                    return when (output) {
                        GuideNetwork::class.java -> {
                            GuideNetwork(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                isFree = false,
                                latestModified = input.latestModified,
                                images = emptyList()
                            ) as R
                        }

                        GuideData::class.java -> {
                            GuideData(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                isFree = false,
                                latestModified = input.latestModified,
                                images = emptyList()
                            ) as R
                        }

                        Guide::class.java -> {
                            Guide(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                isFree = false,
                                images = emptyList()
                            ) as R
                        }

                        else -> throw IllegalArgumentException("Unknown guide specific output type: $output")
                    }
                }

                is GuideNetwork -> {
                    return when (output) {
                        GuideData::class.java -> {
                            GuideData(
                                id = input.id ?: 0L,
                                title = input.title ?: "",
                                content = input.content ?: "",
                                isDraft = input.isDraft ?: true,
                                isFree = false,
                                latestModified = input.latestModified ?: -1L,
                                images = emptyList()
                            ) as R
                        }

                        GuideEntity::class.java -> {
                            GuideEntity(
                                id = input.id ?: 0L,
                                title = input.title ?: "",
                                content = input.content ?: "",
                                isDraft = input.isDraft ?: true,
                                latestModified = input.latestModified ?: -1L
                            ) as R
                        }

                        Guide::class.java -> {
                            Guide(
                                id = input.id ?: 0L,
                                title = input.title ?: "",
                                content = input.content ?: "",
                                isDraft = input.isDraft ?: true,
                                isFree = input.isFree ?: false,
                                images = emptyList()
                            ) as R
                        }

                        else -> throw IllegalArgumentException("Unknown guide specific output type: $output")
                    }
                }

                is GuideData -> {
                    return when (output) {
                        GuideNetwork::class.java -> {
                            GuideNetwork(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                isFree = false,
                                latestModified = input.latestModified,
                                images = emptyList()
                            ) as R
                        }

                        GuideEntity::class.java -> {
                            GuideEntity(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                latestModified = input.latestModified
                            ) as R
                        }

                        Guide::class.java -> {
                            Guide(
                                id = input.id,
                                title = input.title,
                                content = input.content,
                                isDraft = input.isDraft,
                                isFree = input.isFree,
                                images = emptyList()
                            ) as R
                        }

                        else -> throw IllegalArgumentException("Unknown guide specific output type: $output")
                    }
                }

                else -> throw IllegalArgumentException("Unknown guide specific input type: $input")
            }
        }
    }
}
