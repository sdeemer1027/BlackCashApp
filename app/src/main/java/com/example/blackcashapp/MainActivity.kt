package com.example.blackcashapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Camera permission request code
    private val CAMERA_PERMISSION_REQUEST_CODE = 100

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for camera permission before starting WebView
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Permission already granted, initialize WebView
            initializeWebView()
        }
    }

    // Method to initialize WebView
    private fun initializeWebView() {
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // Enable JavaScript if needed

        // Load your Laravel site
        webView.loadUrl("https://dashboard.blk.cash")
    }

    // Handle the camera permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize WebView with camera access
                initializeWebView()
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Camera permission is required to scan QR codes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
