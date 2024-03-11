package com.example.newsapplication.model

import androidx.compose.runtime.Immutable

@Immutable
data class NewsCategory(
    val displayName: String, val category: String
)