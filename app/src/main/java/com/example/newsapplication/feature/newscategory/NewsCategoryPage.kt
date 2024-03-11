package com.example.newsapplication.feature.newscategory


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapplication.Constant
import com.example.newsapplication.Constant.CATEGORY_SCREEN
import com.example.newsapplication.Constant.DETAILS_SCREEN
import com.example.newsapplication.R
import com.example.newsapplication.model.sharedpreference.PreferenceManager
import com.example.newsapplication.ui.view.TopBar

@Composable
fun NewsCategoryPage( navController: NavController, viewModel: NewsCategoryViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state) {
        when (state) {
            is CategoryState.NotStarted -> viewModel.getCategory()
            is CategoryState.SetState -> Unit
            is CategoryState.ErrorState -> Unit
        }
    }
    Column {
        TopBar(heading = "News Category")
        CategoryList(navController = navController, state = state )
    }
}
@Composable
fun CategoryList(navController: NavController,state: CategoryState){
    val categoryList = state.newsCategories
    val context = LocalContext.current
    val pref = remember { PreferenceManager(context) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(10.dp)
    ) {
        items(categoryList.size) {
            Card(
                modifier = Modifier.padding(8.dp),
                onClick =  {
                    pref.saveData("Category",categoryList[it].category)
                    navController.navigate(DETAILS_SCREEN) {
                        popUpTo(CATEGORY_SCREEN) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true}},
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(5.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .padding(5.dp)
                    )

                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = categoryList[it].displayName,
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    val navController = rememberNavController()
    NewsCategoryPage(navController = navController)
}