package com.hiltoncn.demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hiltoncn.demo.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeBinding
    private var cont = 4

    private val handler: Handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1) {
            cont--
            if (cont == 0) {
                starMainActivity()
            } else {
                binding.tvCountdown.text = cont.toString()
                sendMessage()
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sendMessage()
    }

    /**
     * Send countdown message
     * 1 second count
     */
    private fun sendMessage() {
        handler.sendEmptyMessageDelayed(1, 1000)
    }

    /**
     * Jump to homepage
     * Close the current page
     */
    private fun starMainActivity(){
        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        this@WelcomeActivity.finish()
    }

}