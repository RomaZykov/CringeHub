package com.example.data

import com.example.data.model.AdminUserData
import com.example.data.model.GuideData
import com.example.data.model.UserData
import com.example.database.entities.GuideEntity
import com.example.domain.model.AdminRole
import com.example.domain.model.AdminUserDomain
import com.example.domain.model.GuideDomain
import com.example.domain.model.UserDomain
import com.example.network.model.GuideNetwork
import javax.inject.Inject

interface MapperFactory {

    fun <T, R> map(input: T, output: Class<R>): R

    interface UserMapper : MapperFactory {
        class Base @Inject constructor() : UserMapper {
            override fun <T, R> map(input: T, output: Class<R>): R {
                when (input) {
                    is UserData -> {
                        return when (output) {
                            UserDomain::class.java -> {
                                UserDomain(
                                    id = input.id,
                                    userName = input.userName,
                                    isLoggedIn = input.isLoggedIn,
                                ) as R
                            }

                            else -> throw IllegalArgumentException("Unknown user specific output type: $output")
                        }
                    }

                    else -> throw IllegalArgumentException("Unknown user specific input type: $input")
                }
            }
        }
    }

    interface AdminMapper : MapperFactory {
        class Base @Inject constructor() : AdminMapper {
            override fun <T, R> map(input: T, output: Class<R>): R {
                when (input) {
                    is AdminUserData -> {
                        return when (output) {
                            AdminUserDomain::class.java -> {
                                AdminUserDomain(
                                    email = input.email,
                                    adminRole = AdminRole.CONTENT_WRITER
                                ) as R
                            }

                            else -> throw IllegalArgumentException("Unknown admin specific output type: $output")
                        }
                    }

                    else -> throw IllegalArgumentException("Unknown admin specific input type: $input")
                }
            }
        }
    }

    interface GuideMapper : MapperFactory {
        class Base @Inject constructor() : GuideMapper {
            override fun <T, R> map(input: T, output: Class<R>): R {
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

                            GuideDomain::class.java -> {
                                GuideDomain(
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
                                    id = input.id ?: "",
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
                                    id = input.id ?: "",
                                    title = input.title ?: "",
                                    content = input.content ?: "",
                                    isDraft = input.isDraft ?: true,
                                    latestModified = input.latestModified ?: -1L
                                ) as R
                            }

                            GuideDomain::class.java -> {
                                GuideDomain(
                                    id = input.id ?: "",
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

                            GuideDomain::class.java -> {
                                GuideDomain(
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
}
