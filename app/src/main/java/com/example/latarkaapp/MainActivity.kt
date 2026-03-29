package com.example.latarkaapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var toggleButton: Button
    private var serviceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        toggleButton = findViewById(R.id.toggleButton)

        toggleButton.setOnClickListener {
            if (serviceRunning) {
                stopService(Intent(this, ShakeService::class.java))
                serviceRunning = false
            } else {
                val intent = Intent(this, ShakeService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
                serviceRunning = true
            }
            updateUI()
        }

        updateUI()
    }

    private fun updateUI() {
        if (serviceRunning) {
            statusText.text = "Latarka aktywna\nPotrząśnij telefonem"
            toggleButton.text = "WYŁĄCZ"
        } else {
            statusText.text = "Latarka nieaktywna"
            toggleButton.text = "WŁĄCZ"
        }
    }
}