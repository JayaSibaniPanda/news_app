package com.example.newsapplication.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapplication.feature.MainScreen
import com.example.newsapplication.feature.WebViewScreen
import com.example.newsapplication.feature.newscategory.NewsCategoryPage
import com.example.newsapplication.feature.newsdetails.NewsDetailsPage
import com.example.newsapplication.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          Scaffold {
              padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        Navigation()
                    }
                   
                }
            }
        }
}

