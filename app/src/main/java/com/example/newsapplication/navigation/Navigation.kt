package com.example.newsapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapplication.Constant.CATEGORY_SCREEN
import com.example.newsapplication.Constant.DETAILS_SCREEN
import com.example.newsapplication.Constant.MAIN_SCREEN
import com.example.newsapplication.Constant.WEB_SCREEN
import com.example.newsapplication.feature.MainScreen
import com.example.newsapplication.feature.WebViewScreen
import com.example.newsapplication.feature.newscategory.NewsCategoryPage
import com.example.newsapplication.feature.newsdetails.NewsDetailsPage

@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable(MAIN_SCREEN) {
            MainScreen(navController = navController)
        }
        composable(CATEGORY_SCREEN) {
            NewsCategoryPage(navController = navController)
        }
        composable(DETAILS_SCREEN) {
            NewsDetailsPage(navController = navController)
        }
        composable(
            WEB_SCREEN+"/{pageurl}",
            arguments = listOf(navArgument("pageurl") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("pageurl")?.let { pageUrl ->
                WebViewScreen(pageUrl = pageUrl)
            }
        }
    }
}