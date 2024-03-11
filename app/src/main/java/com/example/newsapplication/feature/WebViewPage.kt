package com.example.newsapplication.feature

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsapplication.ui.view.ErrorMessage
import com.example.newsapplication.ui.view.TopBar

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(pageUrl: String) {

    Column {
        TopBar(heading = "News Details")
        if (pageUrl.isNullOrEmpty()) {
            ErrorMessage(error = "URL is not Available.")
        } else {
            AndroidView(
                factory = { context ->
                    return@AndroidView WebView(context).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()

                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(false)
                    }
                },
                update = {
                    it.loadUrl(pageUrl)
                }
            )
        }
    }
}