package com.spongycode.quizzy.screens.play.categorypick

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.domain.repository.QuizRepository
import com.spongycode.quizzy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryPickViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _selectedAmount = mutableStateOf(Amount.FIFTEEN.value.toString())
    val selectedAmount: State<String> = _selectedAmount

    private val _selectedCategory = mutableStateOf("")
    val selectedCategory: State<String> = _selectedCategory

    private val _selectedCategoryId = mutableIntStateOf(0)
    val selectedCategoryId: State<Int> = _selectedCategoryId

    private val _selectedDifficulty = mutableStateOf(Difficulty.MIX.name)
    val selectedDifficulty: State<String> = _selectedDifficulty

    private val _categories = mutableStateListOf<Category>()
    val categories: SnapshotStateList<Category> = _categories

    private val _categoriesState = mutableStateOf<CategoriesState>(CategoriesState.Loading)
    val categoriesState: State<CategoriesState> = _categoriesState

    init {
        getCategories()
    }

    fun onEvent(event: CategoryPickEvent) {
        when (event) {
            is CategoryPickEvent.TappedAmount -> _selectedAmount.value = event.value
            is CategoryPickEvent.TappedCategory -> {
                _selectedCategory.value = event.value.name
                _selectedCategoryId.intValue = event.value.id
            }

            is CategoryPickEvent.TappedDifficulty -> _selectedDifficulty.value = event.value
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            quizRepository.getCategories().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _categoriesState.value = CategoriesState.Error
                    }

                    is Resource.Loading -> {
                        _categoriesState.value = CategoriesState.Loading
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _categories.addAll(result.data)
                        }
                        _categoriesState.value = CategoriesState.Success
                    }
                }
            }
        }
    }

    enum class Difficulty {
        EASY,
        MEDIUM,
        HARD,
        MIX
    }

    enum class Amount(val value: Int) {
        FIFTEEN(15),
        THIRTY(30),
        FORTY_FIVE(45),
        SIXTY(60)
    }
}