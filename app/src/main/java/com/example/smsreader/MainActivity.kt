package com.example.smsreader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar = supportActionBar
        actionBar!!.elevation = 0f
        Handler().postDelayed({
            val i = Intent(
                this@MainActivity,
                Home::class.java
            )
            startActivity(i)
            finish()
        },3000)

    }
}