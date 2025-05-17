package com.example.database

import com.example.database.entities.GuideEntity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class RoomTest : DatabaseTest() {

    private val guidesAsDraft = listOf(
        GuideEntity(
            id = "1",
            title = "Test 1",
            content = "Test content 1",
            isDraft = true,
            latestModified = 0
        ),
        GuideEntity(
            id = "2",
            title = "Test 2",
            content = "Test content 2",
            isDraft = true,
            latestModified = 0
        ),
        GuideEntity(
            id = "3",
            title = "Test 3",
            content = "Test content 3",
            isDraft = true,
            latestModified = 0
        )
    )
    private val guides = listOf(
        GuideEntity(
            id = "4",
            title = "Test 4",
            content = "Test content 4",
            isDraft = false,
            latestModified = 1
        ),
        GuideEntity(
            id = "5",
            title = "Test 5",
            content = "Test content 5",
            isDraft = false,
            latestModified = 2
        ),
    )

    @Test
    @Throws(Exception::class)
    fun saveGuidesAsDraft() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.upsert(it)
        }

        assertEquals(3, guideDao.allGuides().first().size)
        assertEquals(
            GuideEntity(
                id = "1",
                title = "Test 1",
                content = "Test content 1",
                isDraft = true,
                latestModified = 0
            ), guideDao.allGuides().first()[0]
        )
    }

    @Test
    @Throws(Exception::class)
    fun deleteGuideWhenDraft() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.upsert(it)
        }

        guideDao.delete(2)

        assertEquals(2, guideDao.allGuides().first().size)
        assertEquals(
            GuideEntity(
                id = "3",
                title = "Test 3",
                content = "Test content 3",
                isDraft = true,
                latestModified = 0
            ), guideDao.allGuides().first().last()
        )
    }

    @Test
    @Throws(Exception::class)
    fun getGuideById() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.upsert(it)
        }

        val sut = guideDao.getGuide("3")

        assertEquals(
            GuideEntity(
                id = "3",
                title = "Test 3",
                content = "Test content 3",
                isDraft = true,
                latestModified = 0
            ), sut.first()
        )
    }

    @Test
    @Throws(Exception::class)
    fun updateExistingGuide() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.upsert(it)
        }

        guideDao.upsert(
            GuideEntity(
                id = "1",
                title = "Test 1 with additional info",
                content = "Test content 1",
                isDraft = true,
                latestModified = 0
            )
        )

        assertEquals(
            GuideEntity(
                id = "1",
                title = "Test 1 with additional info",
                content = "Test content 1",
                isDraft = true,
                latestModified = 0
            ), guideDao.getGuide("1").first()
        )
    }

    @Test
    @Throws(Exception::class)
    fun fetchAllGuides() = runBlocking {
        val allGuides = guidesAsDraft + guides
        allGuides.forEach {
            guideDao.upsert(it)
        }

        val sut = guideDao.allGuides().first()

        assertEquals(
            allGuides, sut
        )
    }
}