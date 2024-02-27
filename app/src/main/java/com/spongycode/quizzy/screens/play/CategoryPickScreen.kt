package com.spongycode.quizzy.screens.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.screens.components.Topbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickScreen(
    navController: NavHostController,
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp

    Scaffold(topBar = {
        Topbar(
            modifier = Modifier.height((height / 12).dp),
            title = "Pick your category",
            onBackPressed = { navController.navigateUp() }
        )
    }) {
        CategoryPickScreenContent(
            modifier = Modifier.padding(it.calculateTopPadding()),
            height = height,
            width = width
        )
    }
}

@Preview
@Composable
fun CategoryPickScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    viewModel: PlayViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomButton(
            onClick = { viewModel.getQuestions() },
            text = "Start quiz"
        )
    }
}
