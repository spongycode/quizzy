package com.spongycode.quizzy.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.spongycode.quizzy.R
import com.spongycode.quizzy.screens.components.AppBar
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.ui.theme.DecentBlue
import com.spongycode.quizzy.ui.theme.DecentGreen
import com.spongycode.quizzy.utils.Constants

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
    ) {
        Spacer(modifier = Modifier.height(Constants.LARGE_HEIGHT))
        Icon(
            modifier = Modifier.size((height * 0.3).dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "logo",
            tint = DecentGreen
        )
        Spacer(modifier = Modifier.height(Constants.LARGE_HEIGHT))
        CustomButton(
            onClick = { navController?.navigate("personalDetails") },
            text = "Play",
            width = (width * 0.7).toInt(),
            containerColor = DecentBlue,
            contentColor = Color.White
        )
        Spacer(modifier = Modifier.height(Constants.MEDIUM_HEIGHT))
        CustomButton(
            onClick = { navController?.navigate("history") },
            text = "History",
            width = (width * 0.7).toInt(),
            containerColor = DecentGreen,
            contentColor = Color.White
        )
    }
}
