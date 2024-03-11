package com.example.newsapplication.repository

import com.example.newsapplication.model.NewsCategory
import com.example.newsapplication.model.NewsResponse
import javax.inject.Inject

interface Repo {
    suspend fun getTopHeadlines(countryCode: String, category: String): NewsResponse
    suspend fun getNewsCategories(): List<NewsCategory>
}

class RepoImpl @Inject constructor(private val remoteDS: RemoteDS) : Repo {

    override suspend fun getTopHeadlines(countryCode: String, category: String): NewsResponse {
        return remoteDS.getTopHeadlines(countryCode, category)
    }

    override suspend fun getNewsCategories(): List<NewsCategory> {
        return listOf(
            NewsCategory(displayName = "Business", category = "business"),
            NewsCategory(displayName = "Entertainment", category = "entertainment"),
            NewsCategory(displayName = "General", category = "general"),
            NewsCategory(displayName = "Health", category = "health"),
            NewsCategory(displayName = "Science", category = "science"),
            NewsCategory(displayName = "Sports", category = "sports"),
            NewsCategory(displayName = "Technology", category = "technology"),
        )
    }
}