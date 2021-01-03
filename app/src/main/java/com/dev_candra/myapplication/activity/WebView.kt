package com.dev_candra.myapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dev_candra.myapplication.R
import kotlinx.android.synthetic.main.web_activity.*

class WebView : AppCompatActivity(){

    companion object{
        const val HTML_URL = "html_url"
        const val USER_NAME = "user_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        val htmlUrl = intent.getStringExtra(HTML_URL)
        val userName = intent.getStringExtra(USER_NAME)
        htmlUrl?.let { setWebView(it) }
        userName?.let { initToolbar(it) }
    }

    private fun initToolbar(url : String){
        supportActionBar?.title = resources.getString(R.string.nama_developer)
        supportActionBar?.subtitle = url
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        containerWebView.bringChildToFront(progressBar)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: String){
        webview.webViewClient = WebViewClient()
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = (object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        })
        webview.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}