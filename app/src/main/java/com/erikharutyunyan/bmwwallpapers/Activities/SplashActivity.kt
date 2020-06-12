package com.erikharutyunyan.bmwwallpapers.Activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.erikharutyunyan.bmwwallpapers.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), Animation.AnimationListener {
    private lateinit var animZoomIn: Animation
    private lateinit var mPlayer :MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)
        mPlayer = MediaPlayer.create(this, R.raw.bmw__335i)
        mPlayer.start()
        animZoomIn = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom_in
        )
        animZoomIn.setAnimationListener(this)
        splash_image.startAnimation(animZoomIn)
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
        if (p0 == animZoomIn) {
            Handler().postDelayed({
                mPlayer.stop()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)

        }
    }

    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onDestroy() {
        mPlayer.stop()
        super.onDestroy()
    }
}
