package com.example.newsapplication.feature.newsdetails

import android.R.attr.name
import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.sharedpreference.PreferenceManager
import com.example.newsapplication.repository.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val repo: Repo,
    private val preferenceManager: PreferenceManager
): ViewModel() {

    private val _state: MutableStateFlow<NewsDetailsState> =
        MutableStateFlow(NewsDetailsState.NotStarted(emptyList()))
    val state: StateFlow<NewsDetailsState>
        get() = _state

    private val _preference = preferenceManager
    val preference = _preference

    fun getArticles(){
        viewModelScope.launch {
            _state.value = NewsDetailsState.Loading(emptyList())
            delay(1000)
            val country = preference.getData("country","")
            val category = preference.getData("Category","")
           val news =  repo.getTopHeadlines(country,category)
            if(news.articles.isNullOrEmpty()){
                _state.value = NewsDetailsState.Error(news.articles!!,error = "Empty News Articles")
            }else {
                _state.value = NewsDetailsState.Success(news.articles)
            }
        }
    }

}

sealed interface NewsDetailsState {
    val article : List<Article>

    data class NotStarted(override val article: List<Article>) : NewsDetailsState

    data class Loading(override val article: List<Article>) : NewsDetailsState

    data class Success(
       override val article: List<Article>
    ) : NewsDetailsState

    data class Error(override val article: List<Article>,val error: String) : NewsDetailsState
}