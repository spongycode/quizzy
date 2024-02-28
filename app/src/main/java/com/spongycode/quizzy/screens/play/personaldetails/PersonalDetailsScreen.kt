package com.spongycode.quizzy.screens.play.personaldetails

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.screens.components.CustomTextField
import com.spongycode.quizzy.screens.components.Topbar
import com.spongycode.quizzy.ui.theme.DecentGreen
import com.spongycode.quizzy.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(
    navController: NavHostController,
    viewModel: PersonalDetailsViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp
    val name = viewModel.name.value
    val registrationNumber = viewModel.registrationNumber.value
    val grade = viewModel.grade.value
    val context = LocalContext.current


    Scaffold(topBar = {
        Topbar(
            modifier = Modifier.height((height / 12).dp),
            title = "Details",
            onBackPressed = { navController.navigateUp() }
        )
    }) {
        PersonalDetailsScreenContent(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxWidth(),
            height = height,
            width = width,
            name = name,
            registrationNumber = registrationNumber,
            grade = grade,
            onNext = {
                if (name.isNotEmpty() && registrationNumber.isNotEmpty() && grade.isNotEmpty()) {
                    navController.navigate(
                        "categoryPick/${viewModel.name.value.trim()}/${viewModel.registrationNumber.value.trim()}/${viewModel.grade.value.trim()}"
                    )
                } else {
                    Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT).show()
                }
            },
            onEvent = { event -> viewModel.onEvent(event) },
        )
    }
}

@Preview
@Composable
fun PersonalDetailsScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    name: String = "",
    registrationNumber: String = "",
    grade: String = "",
    onNext: () -> Unit = {},
    onEvent: (PersonalDetailsEvent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Constants.LARGE_HEIGHT))
        CustomTextField(
            modifier = Modifier.width((width * 0.9).toInt().dp),
            text = name,
            labelText = "Name",
            placeHolderText = "John Doe",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { onEvent(PersonalDetailsEvent.EnteredName(it)) }
        )
        Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
        CustomTextField(
            modifier = Modifier.width((width * 0.9).toInt().dp),
            text = registrationNumber,
            labelText = "Registration Number",
            placeHolderText = "30LX9123",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { onEvent(PersonalDetailsEvent.EnteredRegistrationNumber(it)) }
        )
        Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
        CustomTextField(
            modifier = Modifier.width((width * 0.9).toInt().dp),
            text = grade,
            labelText = "Grade",
            placeHolderText = "XII",
            shape = RoundedCornerShape(Constants.CORNER_RADIUS_PERCENTAGE),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            onValueChange = { onEvent(PersonalDetailsEvent.EnteredGrade(it)) }
        )
        Spacer(modifier = Modifier.height(Constants.LARGE_HEIGHT))
        CustomButton(
            onClick = { onNext() },
            text = "Next",
            width = (width * 0.8).toInt(),
            containerColor = DecentGreen,
            contentColor = Color.White
        )
    }
}
