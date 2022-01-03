package com.example.moneyNote.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moneyNote.R
import com.example.moneyNote.util.DataBase


class SplashScreenActivity : AppCompatActivity() {
    private val DELAY_TIME = 3000L

    private lateinit var mImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)

        DataBase.getInstance(this)



         val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                mImageView = findViewById(R.id.imageView4)

                Glide.with(applicationContext).load(R.drawable.loading).asGif().into(mImageView);

                val it = Intent(SplashScreenActivity@this, LoginActivity::class.java)
                startActivity(it)
                finish()
            },
            DELAY_TIME
        )
    }
}