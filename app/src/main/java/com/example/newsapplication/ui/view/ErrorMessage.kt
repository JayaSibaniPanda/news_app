package com.example.newsapplication.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessage(error: String){
    Box ( modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(
            text = error,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ErrorMessagePreview(){
    ErrorMessage(error = "No Articles")
}