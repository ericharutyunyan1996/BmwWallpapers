package com.erikharutyunyan.bmwwallpapers.Activities

import android.Manifest
import android.annotation.TargetApi
import android.app.WallpaperManager
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.cleveroad.sy.cyclemenuwidget.OnMenuItemClickListener
import com.erikharutyunyan.bmwwallpapers.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_screen_image.*
import java.util.*

@Suppress("DEPRECATION")
class ScreenImageActivity : BaseActivity(), OnMenuItemClickListener {
    private val code = 1000
    private var image: Bitmap? = null


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_image)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()

        val imagePath = intent.getStringExtra("src")
        val title = intent.getStringExtra("title")
        title?.let {
            toolbarTitle.text = it
        }
        Glide.with(this).load(imagePath).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                val b = resource as BitmapDrawable
                image = b.bitmap
                myZoomageView!!.setImageDrawable(resource)
                progress!!.visibility = View.GONE
                myZoomageView.visibility = View.VISIBLE
            }
        })
        backButton.setOnClickListener { onBackPressed() }
        Log.i("imagepath", imagePath)
        circleMenu.setMenuRes(R.menu.menu)
        circleMenu.setOnMenuItemClickListener(this)
//        saveButton.setOnClickListener {
//
//        }
//        addWalpaper.setOnClickListener {
//
//        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            code ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Please click download button one more time", Toast.LENGTH_SHORT).show()
                    val filename = UUID.randomUUID().toString() + ".jpg"
                    image?.let {
                        MediaStore.Images.Media.insertImage(
                            this.contentResolver,
                            image,
                            filename,
                            ""
                        )
                        Toasty.success(this, "Downloaded", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toasty.error(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroy() {
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
        super.onDestroy()
//        clearCache(this)
    }

    override fun onMenuItemLongClick(view: View?, itemPosition: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMenuItemClick(view: View?, itemPosition: Int) {
        when(itemPosition){
            0->{
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        code

                    )
                    return
                } else {
                    progress.visibility=View.VISIBLE
                    val filename = UUID.randomUUID().toString() + ".jpg"
                    image?.let {
                        MediaStore.Images.Media.insertImage(this.contentResolver, image, filename, "")
                        Toasty.success(this, "Downloaded", Toast.LENGTH_SHORT).show()
                    }
                    progress.visibility=View.GONE
                }
            }
            1->{
                progress.visibility=View.VISIBLE
                val wallpaper = WallpaperManager.getInstance(applicationContext)
                try {
                    wallpaper.setBitmap(image)
                    progress.visibility=View.GONE
                    Toasty.success(this, "Wallpaper was set", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    progress.visibility=View.GONE
                    Toasty.error(this, "Something get wrong", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }


}




