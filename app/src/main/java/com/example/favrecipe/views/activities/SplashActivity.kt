package com.example.favrecipe.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.favrecipe.R
import com.example.favrecipe.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashBinding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        splashBinding.tvSplashText.animation = animation
        animation.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
             //
            }
            override fun onAnimationEnd(p0: Animation?) {
            Handler(Looper.getMainLooper()).postDelayed({
              startActivity(Intent(this@SplashActivity,MainActivity::class.java))
              finish()
            }, 2000 )
            }
            override fun onAnimationRepeat(p0: Animation?) {
                TODO("Not yet implemented")
            }
        })

    }

}
