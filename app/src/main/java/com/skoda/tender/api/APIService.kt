package com.skoda.tender.api

import com.skoda.tender.model.Services
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface APIService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Response<List<Services>>
}