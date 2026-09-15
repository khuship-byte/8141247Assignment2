package com.example.assignment2.data.repository

import com.example.assignment2.data.model.DashboardResponse
import com.example.assignment2.data.model.LoginRequest
import com.example.assignment2.data.model.LoginResponse
import com.example.assignment2.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val apiService: ApiService
) : AnimalRepositoryInterface {

    override suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> {

        return apiService.login(
            LoginRequest(
                username = username,
                password = password
            )
        )
    }

    override suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse> {

        return apiService.getDashboard(
            keypass
        )
    }
}