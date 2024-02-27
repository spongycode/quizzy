package com.spongycode.quizzy.screens.play.categorypick

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.screens.components.CustomDropDownMenu
import com.spongycode.quizzy.screens.components.PlaceholderMessageText
import com.spongycode.quizzy.screens.components.Topbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickScreen(
    navController: NavHostController,
    viewModel: CategoryPickViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp
    val selectedAmount = viewModel.selectedAmount.value
    val selectedDifficulty = viewModel.selectedDifficulty.value
    val selectedCategory = viewModel.selectedCategory.value.toString()
    val categoriesState = viewModel.categoriesState.value

    Scaffold(topBar = {
        Topbar(modifier = Modifier.height((height / 12).dp),
            title = "Pick your category",
            onBackPressed = { navController.navigateUp() })
    }) {

        CategoryPickScreenContent(modifier = Modifier
            .fillMaxSize()
            .padding(it.calculateTopPadding()),
            height = height,
            width = width,
            selectedAmount = selectedAmount,
            selectedCategory = selectedCategory,
            selectedDifficulty = selectedDifficulty,
            categoriesState = categoriesState,
            categories = viewModel.categories,
            onEvent = { event -> viewModel.onEvent(event) },
            onStartQuiz = { navController.navigate("quizPlay") })
    }
}


@Composable
fun CategoryPickScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    selectedAmount: String = "15",
    selectedDifficulty: String = "MIX",
    selectedCategory: String = "MIX",
    categories: SnapshotStateList<Category> = SnapshotStateList(),
    categoriesState: CategoriesState = CategoriesState.Loading,
    onEvent: (CategoryPickEvent) -> Unit = {},
    onStartQuiz: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomDropDownMenu(items = CategoryPickViewModel.Amount.entries.map { it.value.toString() },
                width = width / 2 - 20,
                selectedItem = selectedAmount,
                onClick = { data -> onEvent(CategoryPickEvent.TappedAmount(data)) })
            CustomDropDownMenu(items = CategoryPickViewModel.Difficulty.entries.map { it.name },
                width = width / 2 - 20,
                selectedItem = selectedDifficulty,
                onClick = { data -> onEvent(CategoryPickEvent.TappedDifficulty(data)) })
        }
        LazyColumn(modifier = Modifier.height((height * 0.5).dp)) {
            when (categoriesState) {
                CategoriesState.Error -> {
                    item {
                        PlaceholderMessageText("Some error occurred.")
                    }
                }

                CategoriesState.Loading -> {
                    item {
                        PlaceholderMessageText("Loading categories.")
                    }
                }

                CategoriesState.Success -> {
                    items(categories) { item ->
                        Text(text = item.name)
                    }
                }
            }
        }
        CustomButton(
            onClick = { onStartQuiz() }, text = "Start quiz"
        )
    }
}
