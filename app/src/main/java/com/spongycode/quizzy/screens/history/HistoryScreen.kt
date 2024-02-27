package com.spongycode.quizzy.screens.history

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.screens.components.PlaceholderMessageText
import com.spongycode.quizzy.screens.components.Topbar
import com.spongycode.quizzy.screens.history.components.CustomList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    Scaffold(topBar = {
        Topbar(onBackPressed = { navController.navigateUp() }, title = "Past Quizzes")
    }) {
        when (viewModel.historyState.value) {
            HistoryState.Error -> PlaceholderMessageText("Oops, some error occurred.")
            HistoryState.Loading -> PlaceholderMessageText("Loading past quizzes..")
            HistoryState.Success -> CustomList(
                quizzes = viewModel.quizzes,
                it.calculateTopPadding()
            )
        }
    }
}