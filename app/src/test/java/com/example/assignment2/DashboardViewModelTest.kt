package com.example.assignment2

import com.example.assignment2.data.model.Animal
import com.example.assignment2.data.model.DashboardResponse
import com.example.assignment2.data.repository.AnimalRepositoryInterface
import com.example.assignment2.ui.dashboard.DashboardState
import com.example.assignment2.ui.dashboard.DashboardViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var repository: AnimalRepositoryInterface
    private lateinit var viewModel: DashboardViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()

        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `blank keypass returns error`() = runTest {

        viewModel.loadDashboard("")

        val state = viewModel.dashboardState.value

        assertTrue(
            state is DashboardState.Error
        )

        assertEquals(
            "Unable to load dashboard.",
            (state as DashboardState.Error).message
        )
    }

    @Test
    fun `successful dashboard response returns animals`() = runTest {

        val animals = listOf(
            Animal(
                species = "African Elephant",
                scientificName = "Loxodonta africana",
                habitat = "Savanna",
                diet = "Herbivore",
                conservationStatus = "Vulnerable",
                averageLifespan = 60,
                description = "Test elephant description"
            ),
            Animal(
                species = "Giant Panda",
                scientificName = "Ailuropoda melanoleuca",
                habitat = "Temperate forest",
                diet = "Herbivore",
                conservationStatus = "Vulnerable",
                averageLifespan = 20,
                description = "Test panda description"
            )
        )

        val dashboardResponse = DashboardResponse(
            entities = animals,
            entityTotal = animals.size
        )

        coEvery {
            repository.getDashboard("animals")
        } returns Response.success(
            dashboardResponse
        )

        viewModel.loadDashboard("animals")

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value

        assertTrue(
            state is DashboardState.Success
        )

        val successState =
            state as DashboardState.Success

        assertEquals(
            2,
            successState.animals.size
        )

        assertEquals(
            "African Elephant",
            successState.animals[0].species
        )

        assertEquals(
            "Giant Panda",
            successState.animals[1].species
        )

        coVerify(exactly = 1) {
            repository.getDashboard("animals")
        }
    }

    @Test
    fun `empty dashboard response returns no animals error`() = runTest {

        val dashboardResponse = DashboardResponse(
            entities = emptyList(),
            entityTotal = 0
        )

        coEvery {
            repository.getDashboard("animals")
        } returns Response.success(
            dashboardResponse
        )

        viewModel.loadDashboard("animals")

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value

        assertTrue(
            state is DashboardState.Error
        )

        assertEquals(
            "No animals found.",
            (state as DashboardState.Error).message
        )
    }

    @Test
    fun `unsuccessful dashboard response returns load error`() = runTest {

        coEvery {
            repository.getDashboard("animals")
        } returns Response.error(
            500,
            okhttp3.ResponseBody.create(
                null,
                ""
            )
        )

        viewModel.loadDashboard("animals")

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value

        assertTrue(
            state is DashboardState.Error
        )

        assertEquals(
            "Unable to load animals.",
            (state as DashboardState.Error).message
        )
    }

    @Test
    fun `network exception returns connection error`() = runTest {

        coEvery {
            repository.getDashboard("animals")
        } throws RuntimeException(
            "Network unavailable"
        )

        viewModel.loadDashboard("animals")

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value

        assertTrue(
            state is DashboardState.Error
        )

        assertEquals(
            "Unable to connect. Please check your internet connection.",
            (state as DashboardState.Error).message
        )
    }
}