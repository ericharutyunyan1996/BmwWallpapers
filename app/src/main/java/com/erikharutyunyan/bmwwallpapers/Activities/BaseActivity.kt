package com.erikharutyunyan.bmwwallpapers.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.erikharutyunyan.bmwwallpapers.R

open class BaseActivity : AppCompatActivity() {

    fun openFragment(fragment: Fragment, withAnimation: Boolean, withBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (withAnimation) {
            transaction.setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit
            )
        }
        transaction.replace(
            R.id.fragment_conteiner,
            fragment,
            fragment.javaClass.simpleName
        )
        if (withBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
    }

    fun clearCache(context: Context) {
        context.cacheDir.deleteRecursively()
    }
}
