package com.example.assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.repository.AnimalRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AnimalRepositoryInterface
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<LoginState>(
            LoginState.Idle
        )

    val loginState: StateFlow<LoginState> =
        _loginState.asStateFlow()


    fun login(
        username: String,
        password: String
    ) {

        // Check for empty fields
        if (
            username.isBlank() ||
            password.isBlank()
        ) {

            _loginState.value =
                LoginState.Error(
                    "Please enter your username and password."
                )

            return
        }


        viewModelScope.launch {

            _loginState.value =
                LoginState.Loading

            try {

                val response =
                    repository.login(
                        username,
                        password
                    )


                if (response.isSuccessful) {

                    val keypass =
                        response.body()?.keypass


                    if (!keypass.isNullOrBlank()) {

                        _loginState.value =
                            LoginState.Success(
                                keypass
                            )

                    } else {

                        _loginState.value =
                            LoginState.Error(
                                "Unable to complete login. Please try again."
                            )
                    }

                } else {

                    _loginState.value =
                        LoginState.Error(
                            "Invalid username or password."
                        )
                }

            } catch (e: Exception) {

                _loginState.value =
                    LoginState.Error(
                        "Unable to connect. Please check your internet connection."
                    )
            }
        }
    }
}


sealed class LoginState {

    object Idle : LoginState()

    object Loading : LoginState()

    data class Success(
        val keypass: String
    ) : LoginState()

    data class Error(
        val message: String
    ) : LoginState()
}