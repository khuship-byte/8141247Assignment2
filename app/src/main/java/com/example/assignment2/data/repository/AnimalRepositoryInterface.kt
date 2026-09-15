package com.example.assignment2.data.repository

import com.example.assignment2.data.model.DashboardResponse
import com.example.assignment2.data.model.LoginResponse
import retrofit2.Response

interface AnimalRepositoryInterface {

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse>

    suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse>
}