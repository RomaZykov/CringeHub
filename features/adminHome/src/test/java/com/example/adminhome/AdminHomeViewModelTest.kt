package com.example.adminhome

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import com.example.adminhome.model.GuideUi
import com.example.adminhome.model.InitialUi
import com.example.adminnavigation.DraftRouteProvider
import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.Route
import com.example.domain.model.GuideDomain
import com.example.test.repository.FakeAdminGuideRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AdminHomeViewModelTest {

    private class FakeGuideCreationRouteProvider : GuideCreationRouteProvider {
        var wasRouteCalled = false
        override fun route(): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }

    private class FakeDraftRouteProvider : DraftRouteProvider {
        var wasRouteCalled = false
        override fun route(id: String): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }

    private lateinit var fakeGuideCreationRouteProvider: FakeGuideCreationRouteProvider
    private lateinit var fakeDraftRouteProvider: FakeDraftRouteProvider
    private lateinit var fakeAdminGuideRepository: FakeAdminGuideRepository
    private lateinit var context: Context

    // Similar as in fakeGuideRepository but with ui model
    private val expectedGuideListToManipulate: MutableList<GuideUi> = mutableListOf(
        GuideUi(
            id = "1",
            title = "Test title 1",
            content = "Test content 1",
            isFree = true,
            isDraft = true
        ),
        GuideUi(
            id = "2",
            title = "Test title 2",
            content = "Test content 2",
            isFree = true,
            isDraft = true
        ),
        GuideUi(
            id = "3",
            title = "Test title 3",
            content = "Test content 3",
            isFree = true,
            isDraft = true
        ),
        GuideUi(
            id = "4",
            title = "Test title 4",
            content = "Test content 4",
            isFree = true,
            isDraft = false
        ),
        GuideUi(
            id = "5",
            title = "Test title 5",
            content = "Test content 5",
            isFree = true,
            isDraft = false
        )
    )

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        fakeGuideCreationRouteProvider = FakeGuideCreationRouteProvider()
        fakeDraftRouteProvider = FakeDraftRouteProvider()
        fakeAdminGuideRepository = FakeAdminGuideRepository().apply {
            val guides = listOf(
                GuideDomain(
                    id = "1",
                    title = "Test title 1",
                    content = "Test content 1",
                    isFree = true,
                    isDraft = true,
                    images = emptyList()
                ),
                GuideDomain(
                    id = "2",
                    title = "Test title 2",
                    content = "Test content 2",
                    isFree = true,
                    isDraft = true,
                    images = emptyList()
                ),
                GuideDomain(
                    id = "3",
                    title = "Test title 3",
                    content = "Test content 3",
                    isFree = true,
                    isDraft = true,
                    images = emptyList()
                ),
                GuideDomain(
                    id = "4",
                    title = "Test title 4",
                    content = "Test content 4",
                    isFree = true,
                    isDraft = false,
                    images = emptyList()
                ),
                GuideDomain(
                    id = "5",
                    title = "Test title 5",
                    content = "Test content 5",
                    isFree = true,
                    isDraft = false,
                    images = emptyList()
                )
            )
            this.guides.addAll(guides)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial state has drafts and published guides if any exist`() =
        runTest {
            val viewModel = AdminHomeViewModel.Base(
                guideCreationRouteProvider = fakeGuideCreationRouteProvider,
                draftRouteProvider = fakeDraftRouteProvider,
                guideRepository = fakeAdminGuideRepository,
                UnconfinedTestDispatcher()
            )

            viewModel.init()

            val actualUiState = viewModel.adminHomeUiStateFlow().value
            val expectedUiState = InitialUi(expectedGuideListToManipulate)

            assertEquals(expectedUiState, actualUiState)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial state does not have drafts and published guides if none exist`() = runTest {
        val viewModel = AdminHomeViewModel.Base(
            guideCreationRouteProvider = fakeGuideCreationRouteProvider,
            draftRouteProvider = fakeDraftRouteProvider,
            guideRepository = fakeAdminGuideRepository.apply {
                guides.clear()
            },
            UnconfinedTestDispatcher()
        )

        viewModel.init()

        val actualUiState = viewModel.adminHomeUiStateFlow().value
        val expectedUiState = InitialUi(emptyList())

        assertEquals(expectedUiState, actualUiState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial state has only drafts if exist but published guides non`() = runTest {
        val viewModel = AdminHomeViewModel.Base(
            guideCreationRouteProvider = fakeGuideCreationRouteProvider,
            draftRouteProvider = fakeDraftRouteProvider,
            guideRepository = fakeAdminGuideRepository.apply {
                guides.removeAll { !it.isDraft }
            },
            UnconfinedTestDispatcher()
        )

        viewModel.init()

        val actualUiState = viewModel.adminHomeUiStateFlow().value
        val expectedUiState = InitialUi(expectedGuideListToManipulate.filter { it.isDraft })

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun firstDraftDeleted_afterDeleteIconOnDraftClicked() {

    }
}