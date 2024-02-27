package com.spongycode.quizzy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spongycode.quizzy.screens.history.HistoryScreen
import com.spongycode.quizzy.screens.home.HomeScreen
import com.spongycode.quizzy.screens.play.categorypick.CategoryPickScreen
import com.spongycode.quizzy.screens.play.personaldetails.PersonalDetailsScreen
import com.spongycode.quizzy.screens.play.quizplay.QuizPlayScreen

@Composable
fun NavContainer(startDestination: String) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "home") {
            HomeScreen(navController = navController)
        }
        composable(route = "personalDetails") {
            PersonalDetailsScreen(navController = navController)
        }
        composable(route = "categoryPick") {
            CategoryPickScreen(navController = navController)
        }
        composable(route = "quizPlay") {
            QuizPlayScreen(navController = navController)
        }
        composable(route = "history") {
            HistoryScreen(navController = navController)
        }
    }
}