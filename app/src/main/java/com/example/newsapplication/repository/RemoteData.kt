package com.example.newsapplication.repository

import com.example.newsapplication.model.ApiKey
import com.example.newsapplication.model.NewsResponse
import com.example.newsapplication.network.RetrofitApiInterface
import javax.inject.Inject

interface RemoteDS {
    suspend fun getTopHeadlines(countryCode: String, category: String): NewsResponse
}


class RemoteDSImpl @Inject constructor(
    private val apiKey: ApiKey, private val api: RetrofitApiInterface
) : RemoteDS {

    override suspend fun getTopHeadlines(countryCode: String, category: String): NewsResponse {
        return api.getTopHeadlines(apiKey.key, countryCode, category)
    }

}