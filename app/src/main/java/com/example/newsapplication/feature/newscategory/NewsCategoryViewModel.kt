package com.example.newsapplication.feature.newscategory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.model.NewsCategory
import com.example.newsapplication.repository.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.newsapplication.model.sharedpreference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsCategoryViewModel @Inject constructor(
    private val repo: Repo
): ViewModel(){

    private val _state: MutableStateFlow<CategoryState> =
        MutableStateFlow(CategoryState.NotStarted())
    val state: StateFlow<CategoryState>
        get() = _state

    fun getCategory(){
        viewModelScope.launch() {
            val categories = repo.getNewsCategories()
            val selectedCategory = categories.firstOrNull()
            val selectedCategoryIndex = 0
            _state.value = CategoryState.SetState(
                    newsCategories = categories,
                    selectedCategory = selectedCategory,
                    selectedCategoryIndex = selectedCategoryIndex
            )
        }
    }

}

sealed interface CategoryState{
    val newsCategories: List<NewsCategory>
    data class NotStarted(override val newsCategories: List<NewsCategory> = emptyList()): CategoryState
    data class SetState(
        override val newsCategories: List<NewsCategory>,
        val selectedCategory: NewsCategory?,
        val selectedCategoryIndex: Int
    ): CategoryState
    data class ErrorState(override val newsCategories: List<NewsCategory>,
                          val error: String? = null): CategoryState

}