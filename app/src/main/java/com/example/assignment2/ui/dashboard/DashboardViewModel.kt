package com.example.assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.model.Animal
import com.example.assignment2.data.repository.AnimalRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AnimalRepositoryInterface
) : ViewModel() {

    private val _dashboardState =
        MutableStateFlow<DashboardState>(
            DashboardState.Idle
        )

    val dashboardState: StateFlow<DashboardState> =
        _dashboardState.asStateFlow()


    fun loadDashboard(
        keypass: String
    ) {

        // Check that a valid keypass was received
        if (keypass.isBlank()) {

            _dashboardState.value =
                DashboardState.Error(
                    "Unable to load dashboard."
                )

            return
        }


        viewModelScope.launch {

            _dashboardState.value =
                DashboardState.Loading

            try {

                val response =
                    repository.getDashboard(
                        keypass
                    )


                if (response.isSuccessful) {

                    val animals =
                        response.body()
                            ?.entities
                            .orEmpty()


                    if (animals.isNotEmpty()) {

                        _dashboardState.value =
                            DashboardState.Success(
                                animals
                            )

                    } else {

                        _dashboardState.value =
                            DashboardState.Error(
                                "No animals found."
                            )
                    }

                } else {

                    _dashboardState.value =
                        DashboardState.Error(
                            "Unable to load animals."
                        )
                }

            } catch (e: Exception) {

                _dashboardState.value =
                    DashboardState.Error(
                        "Unable to connect. Please check your internet connection."
                    )
            }
        }
    }
}


sealed class DashboardState {

    object Idle : DashboardState()

    object Loading : DashboardState()

    data class Success(
        val animals: List<Animal>
    ) : DashboardState()

    data class Error(
        val message: String
    ) : DashboardState()
}