package com.example.assignment2

import com.example.assignment2.data.model.LoginResponse
import com.example.assignment2.data.repository.AnimalRepositoryInterface
import com.example.assignment2.ui.login.LoginState
import com.example.assignment2.ui.login.LoginViewModel
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
class LoginViewModelTest {

    private lateinit var repository: AnimalRepositoryInterface
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()

        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `blank username returns error`() = runTest {

        viewModel.login(
            "",
            "Password"
        )

        val state = viewModel.loginState.value

        assertTrue(
            state is LoginState.Error
        )

        assertEquals(
            "Please enter your username and password.",
            (state as LoginState.Error).message
        )
    }

    @Test
    fun `blank password returns error`() = runTest {

        viewModel.login(
            "8141247",
            ""
        )

        val state = viewModel.loginState.value

        assertTrue(
            state is LoginState.Error
        )

        assertEquals(
            "Please enter your username and password.",
            (state as LoginState.Error).message
        )
    }

    @Test
    fun `successful login returns keypass`() = runTest {

        val loginResponse =
            LoginResponse(
                keypass = "animals"
            )

        coEvery {
            repository.login(
                any(),
                any()
            )
        } returns Response.success(
            loginResponse
        )

        viewModel.login(
            "8141247",
            "TestPassword"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state =
            viewModel.loginState.value

        assertTrue(
            state is LoginState.Success
        )

        assertEquals(
            "animals",
            (state as LoginState.Success).keypass
        )

        coVerify(exactly = 1) {
            repository.login(
                "8141247",
                "TestPassword"
            )
        }
    }

    @Test
    fun `successful response without keypass returns error`() = runTest {

        val loginResponse =
            LoginResponse(
                keypass = ""
            )

        coEvery {
            repository.login(
                any(),
                any()
            )
        } returns Response.success(
            loginResponse
        )

        viewModel.login(
            "8141247",
            "TestPassword"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state =
            viewModel.loginState.value

        assertTrue(
            state is LoginState.Error
        )

        assertEquals(
            "Unable to complete login. Please try again.",
            (state as LoginState.Error).message
        )
    }

    @Test
    fun `invalid login returns error`() = runTest {

        coEvery {
            repository.login(
                any(),
                any()
            )
        } returns Response.error(
            401,
            okhttp3.ResponseBody.create(
                null,
                ""
            )
        )

        viewModel.login(
            "8141247",
            "WrongPassword"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state =
            viewModel.loginState.value

        assertTrue(
            state is LoginState.Error
        )

        assertEquals(
            "Invalid username or password.",
            (state as LoginState.Error).message
        )
    }

    @Test
    fun `network exception returns connection error`() = runTest {

        coEvery {
            repository.login(
                any(),
                any()
            )
        } throws RuntimeException(
            "Network unavailable"
        )

        viewModel.login(
            "8141247",
            "TestPassword"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state =
            viewModel.loginState.value

        assertTrue(
            state is LoginState.Error
        )

        assertEquals(
            "Unable to connect. Please check your internet connection.",
            (state as LoginState.Error).message
        )
    }
}