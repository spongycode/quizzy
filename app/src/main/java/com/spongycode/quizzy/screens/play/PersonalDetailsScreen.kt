package com.spongycode.quizzy.screens.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.screens.components.CustomTextField
import com.spongycode.quizzy.screens.components.Topbar
import com.spongycode.quizzy.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(
    navController: NavHostController,
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp

    Scaffold(topBar = {
        Topbar(
            modifier = Modifier.height((height / 12).dp),
            title = "Details",
            onBackPressed = { navController.navigateUp() }
        )
    }) {
        PersonalDetailsScreenContent(
            modifier = Modifier.padding(it.calculateTopPadding()),
            height = height,
            width = width,
            onNext = { navController.navigate("categoryPick") }
        )
    }
}

@Preview
@Composable
fun PersonalDetailsScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    viewModel: PlayViewModel = hiltViewModel(),
    onNext: () -> Unit = {}
) {
    val name = viewModel.name.value
    val registrationNumber = viewModel.registrationNumber.value
    val grade = viewModel.grade.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomTextField(
            text = name,
            labelText = "Name",
            placeHolderText = "Name",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { viewModel.onEvent(PlayEvent.EnteredName(it)) }
        )
        CustomTextField(
            text = registrationNumber,
            labelText = "Registration Number",
            placeHolderText = "Registration Number",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { viewModel.onEvent(PlayEvent.EnteredRegistrationNumber(it)) }
        )
        CustomTextField(
            text = grade,
            labelText = "Grade",
            placeHolderText = "Grade",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { viewModel.onEvent(PlayEvent.EnteredGrade(it)) }
        )
        CustomButton(
            onClick = { onNext() },
            displayText = "Next"
        )
    }
}
