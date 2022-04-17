package com.example.smsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FullMessageScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_message_screen)
        val message = intent.getStringExtra("name")
        val message2 = intent.getStringExtra("msg")
        val messageTextView: TextView = findViewById(R.id.header)
        messageTextView.text = message
        val messageTextView2: TextView = findViewById(R.id.msg)
        messageTextView2.text = message2
    }
}