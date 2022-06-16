package com.rivzdev.chatbotapp.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivzdev.chatbotapp.databinding.ActivityChatBinding
import com.rivzdev.chatbotapp.model.response.ChatBotResponse
import com.rivzdev.chatbotapp.viewmodel.ChatBotViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ListChatAdapter
    private val mainViewModel by viewModels<ChatBotViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecycleListChat()
        sendChat()
    }

    private fun showRecycleListChat() {
        adapter = ListChatAdapter()
        binding.apply {
            rvChat.layoutManager = LinearLayoutManager(this@ChatActivity)
            rvChat.adapter = adapter
            rvChat.setHasFixedSize(true)
        }
    }

    private fun sendChat() {

        binding.apply {
            btnSend.setOnClickListener {
                if (edtChat.text.isNullOrEmpty()) {
                    Toast.makeText(this@ChatActivity, "Please enter a text", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    adapter.addChatToList(ChatBotResponse(edtChat.text.toString(), ""))
                    mainViewModel.getMessageBot(ChatBotResponse(edtChat.text.toString(), ""))
                    mainViewModel.message.observe(this@ChatActivity) {
                        adapter.addChatToList(it)
                    }
                }
            }
        }
    }
}