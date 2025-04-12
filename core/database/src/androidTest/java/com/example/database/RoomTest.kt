package com.example.database

import com.example.database.entities.GuideEntity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class RoomTest : DatabaseTest() {

    private val guidesAsDraft = listOf(
        GuideEntity(
            id = 1,
            title = "Test 1",
            content = "Test content 1",
            isDraft = true
        ),
        GuideEntity(
            id = 2,
            title = "Test 2",
            content = "Test content 2",
            isDraft = true
        ),
        GuideEntity(
            id = 3,
            title = "Test 3",
            content = "Test content 3",
            isDraft = true
        )
    )
    private val guides = listOf(
        GuideEntity(
            id = 4,
            title = "Test 4",
            content = "Test content 4",
            isDraft = false
        ),
        GuideEntity(
            id = 5,
            title = "Test 5",
            content = "Test content 5",
            isDraft = false
        ),
    )

    @Test
    @Throws(Exception::class)
    fun saveGuidesAsDraft() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.insert(it)
        }

        assertEquals(3, guideDao.allGuideDrafts().size)
        assertEquals(
            GuideEntity(
                id = 1,
                title = "Test 1",
                content = "Test content 1",
                isDraft = true
            ), guideDao.allGuideDrafts().first()
        )
    }

    @Test
    @Throws(Exception::class)
    fun deleteGuideWhenDraft() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.insert(it)
        }

        guideDao.delete(2)

        assertEquals(2, guideDao.allGuideDrafts().size)
        assertEquals(
            GuideEntity(
                id = 3,
                title = "Test 3",
                content = "Test content 3",
                isDraft = true
            ), guideDao.allGuideDrafts().last()
        )
    }

    @Test
    @Throws(Exception::class)
    fun getGuideById() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.insert(it)
        }

        val sut = guideDao.getGuide(3)

        assertEquals(
            GuideEntity(
                id = 3,
                title = "Test 3",
                content = "Test content 3",
                isDraft = true
            ), sut
        )
    }

    @Test
    @Throws(Exception::class)
    fun updateExistingGuide() = runBlocking {
        guidesAsDraft.forEach {
            guideDao.insert(it)
        }

        guideDao.insert(
            GuideEntity(
                id = 1,
                title = "Test 1 with additional info",
                content = "Test content 1",
                isDraft = true
            )
        )

        assertEquals(
            GuideEntity(
                id = 1,
                title = "Test 1 with additional info",
                content = "Test content 1",
                isDraft = true
            ), guideDao.getGuide(1)
        )
    }

    @Test
    @Throws(Exception::class)
    fun fetchAllGuides() = runBlocking {
        val allGuides = guidesAsDraft + guides
        allGuides.forEach {
            guideDao.insert(it)
        }

        val sut = guideDao.allGuides()

        assertEquals(
            allGuides, sut
        )
    }
}