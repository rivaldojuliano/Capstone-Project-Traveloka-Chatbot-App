package com.rivzdev.chatbotapp.ui.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivzdev.chatbotapp.R
import com.rivzdev.chatbotapp.databinding.ActivityChatBinding
import com.rivzdev.chatbotapp.model.response.ChatBotResponse
import com.rivzdev.chatbotapp.ui.home.HomePage
import com.rivzdev.chatbotapp.viewmodel.ChatBotViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ListChatAdapter
    private val mainViewModel by viewModels<ChatBotViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListChatAdapter()
        showRecycleListChat()
        buttonClick()
        setupViewModel()
    }

    private fun showRecycleListChat() {
        binding.apply {
            rvChat.setHasFixedSize(true)
            rvChat.layoutManager = LinearLayoutManager(this@ChatActivity)
            rvChat.adapter = adapter
        }
    }

    private fun buttonClick() {

        binding.apply {
            btnSend.setOnClickListener {
                if (edtChat.text.isNullOrEmpty()) {
                    Toast.makeText(this@ChatActivity, "Please enter a text", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    adapter.addChatToList(ChatBotResponse(edtChat.text.toString(), ""))
                    mainViewModel.getMessageBot(ChatBotResponse(edtChat.text.toString(), ""))
                    binding.edtChat.text.clear()
                    rvChat.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

        binding.apply {
            btnEndConversation.setOnClickListener {
                AlertDialog.Builder(this@ChatActivity).apply {
                    setTitle(resources.getString(R.string.end_conversation))
                    setMessage(resources.getString(R.string.end_conversation_question))
                    setPositiveButton(resources.getString(R.string.exit)) {_, _ ->
                        val intent = Intent(context, HomePage::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel.message.observe(this@ChatActivity) {
            adapter.addChatToList(it)
        }
    }

    override fun onBackPressed() {
    }
}