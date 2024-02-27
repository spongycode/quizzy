package com.spongycode.quizzy.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.spongycode.quizzy.R
import com.spongycode.quizzy.screens.components.AppBar
import com.spongycode.quizzy.screens.components.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp

    Scaffold(topBar = {
        AppBar(
            title = "Quizzy",
            modifier = Modifier.height((height / 12).dp)
        )
    }) {
        HomeScreenContent(
            modifier = Modifier.padding(it.calculateTopPadding()),
            height = height,
            width = width,
            navController = navController
        )
    }
}

@Preview
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    navController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "logo"
        )
        CustomButton(onClick = { navController?.navigate("personalDetails") }, displayText = "Play")
        CustomButton(onClick = { /*TODO*/ }, displayText = "History")
    }
}
