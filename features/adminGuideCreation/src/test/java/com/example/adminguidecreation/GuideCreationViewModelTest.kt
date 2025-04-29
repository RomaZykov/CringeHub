package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.adminguidecreation.model.DialogUi
import com.example.test.repository.FakeAdminGuideRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GuideCreationViewModelTest {

    @Test
    fun loadBooksData_invokes_use_case_and_updates_state_flow() = runTest {
        val fakeGuideRepository = FakeAdminGuideRepository()
        val viewModel = GuideCreationViewModel.Base(
            fakeGuideRepository
        )

        val hasInternetConnection = false
        viewModel.saveContent(hasInternetConnection)

        val actualUiState = viewModel.guideCreationUiStateFlow().value
        val expectedUiState = DialogUi(

        )
        assertEquals(expectedUiState, actualUiState)
    }

    private class FakeHomeRouteProvider {
        var wasRouteCalled = false
        override fun route(id: String): Route {
            wasRouteCalled = true
            return object : Route {
                @Composable
                override fun Content(navController: NavController) = Unit
            }
        }
    }
}