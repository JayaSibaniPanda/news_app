package com.example.newsapplication.network

import com.example.newsapplication.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiInterface {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String, @Query("country") countryCode: String, @Query("category") category: String
    ): NewsResponse

}