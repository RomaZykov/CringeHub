package com.example.adminguidecreation

import com.example.adminguidecreation.components.ContentItem
import com.example.adminguidecreation.model.GuideUi
import com.example.common.core.DispatcherList
import com.example.domain.model.GuideDomain
import com.example.test.repository.FakeAdminGuideRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class GuideCreationViewModelTest {

    private lateinit var viewModel: GuideCreationViewModel
    private lateinit var fakeAdminGuideRepository: FakeAdminGuideRepository

    private lateinit var fakeDispatcherList: DispatcherList
    private var wasUiDispatcherCalled: Boolean = false
    private var wasIoDispatcherCalled: Boolean = false

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        fakeDispatcherList = object : DispatcherList {
            override fun ui(): CoroutineDispatcher {
                wasUiDispatcherCalled = true
                return UnconfinedTestDispatcher()
            }

            override fun io(): CoroutineDispatcher {
                wasIoDispatcherCalled = true
                return UnconfinedTestDispatcher()
            }
        }
        fakeAdminGuideRepository = FakeAdminGuideRepository()
        viewModel = GuideCreationViewModel.Base(
            fakeAdminGuideRepository,
            fakeDispatcherList
        )
    }

    @Test
    fun loadGuideData_updatesStateFlow() = runTest {
        fakeAdminGuideRepository.guides.add(
            0,
            GuideDomain(
                "1", "test title 1",
                "test content paragraph 1\ntest content paragraph 2",
                isFree = false,
                isDraft = true,
                media = emptyList()
            )
        )

        viewModel.loadGuideWithId("1")

        assertTrue(wasIoDispatcherCalled)
        assertFalse(wasUiDispatcherCalled)
        assertEquals(
            GuideUi(
                "1",
                "test title 1",
                listOf(
                    ContentItem.TextItem("test content paragraph 1"),
                    ContentItem.TextItem("test content paragraph 2")
                )
            ), viewModel.guideCreationUiStateFlow().value
        )
    }
}