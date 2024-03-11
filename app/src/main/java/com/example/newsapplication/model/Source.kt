package com.example.newsapplication.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Source(
    @SerializedName("id") val id: String? = null, @SerializedName("name") val name: String? = null
)