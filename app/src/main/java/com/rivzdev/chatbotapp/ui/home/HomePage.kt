package com.rivzdev.chatbotapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rivzdev.chatbotapp.databinding.ActivityHomePageBinding
import com.rivzdev.chatbotapp.ui.chat.ChatActivity

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClicked()
    }

    private fun buttonClicked() {
        binding.apply {
            btnChat.setOnClickListener {
                startActivity(Intent(this@HomePage, ChatActivity::class.java))
            }
        }
    }
}