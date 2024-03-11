package com.example.newsapplication.feature.newsdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.newsapplication.Constant.DETAILS_SCREEN
import com.example.newsapplication.Constant.WEB_SCREEN
import com.example.newsapplication.R
import com.example.newsapplication.model.Article
import com.example.newsapplication.ui.view.ErrorMessage
import com.example.newsapplication.ui.view.LoadingProgress
import com.example.newsapplication.ui.view.TopBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsDetailsPage(navController: NavController,viewModel: NewsDetailsViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState().value
    var errorMessage: String = "No Articles.."

    LaunchedEffect(state) {
        when (state) {
            is NewsDetailsState.NotStarted -> viewModel.getArticles()
            is NewsDetailsState.Loading -> Unit
            is NewsDetailsState.Success -> Unit
            is NewsDetailsState.Error ->{
                errorMessage = state.error
            }
        }
    }
    Column {
        TopBar(heading = "Top Headlines")
        NewsListing(state = state, news = state.article, onNewsClick = { news ->
            val url = news.url
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            navController.navigate("$WEB_SCREEN/$encodedUrl") {
                popUpTo(DETAILS_SCREEN) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
            errorMessage = errorMessage)
    }

}
    @Composable
    private fun NewsListing(state:NewsDetailsState, news: List<Article>, onNewsClick: (Article) -> Unit, errorMessage: String) {
        when(state){
            is NewsDetailsState.NotStarted -> Unit

            is NewsDetailsState.Loading -> LoadingProgress()

            is NewsDetailsState.Success -> NewsLists(news = news, onNewsClick = onNewsClick)

            is NewsDetailsState.Error ->  ErrorMessage(error = errorMessage)

        }
    }

@Composable
fun NewsLists(news: List<Article>, onNewsClick: (Article) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(news) { index, item ->
            NewsItem(news = item, onNewsClick = onNewsClick)
        }
    }
}


@Composable
private fun NewsItem(news: Article, onNewsClick: (Article) -> Unit) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onNewsClick(news) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    model = news.urlToImage,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = ""
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp), Arrangement.SpaceBetween
            ) {
                Text(text = news.author ?: "", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                news.publishedAt?.let { Text(text = it, fontSize = 12.sp, fontWeight = FontWeight.Normal) }
            }
            if (news.title != null) {
                Text(text = news.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun NewsDetailsPreview(){
    val navController = rememberNavController()
    NewsDetailsPage(navController = navController)
}
