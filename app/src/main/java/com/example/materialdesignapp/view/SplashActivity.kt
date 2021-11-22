package com.example.materialdesignapp.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import com.example.materialdesignapp.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_splash.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_splash)

        animationDelay(textView1, 500)
        animationDelay(textView2, 700)
        animationDelay(textView3, 900)
        animationDelay(textView4, 1100)

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

    private fun animationDelay(view: View, delay: Long) {
        handler.postDelayed({
            view.animate()
                .translationY(1000f)
                .duration = 900
        }, delay)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}