package com.erikharutyunyan.bmwwallpapers.Activities

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.erikharutyunyan.bmwwallpapers.Fragments.MainFragment
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnFragmentInteraction
import com.erikharutyunyan.bmwwallpapers.R

class MainActivity : BaseActivity(), OnFragmentInteraction {
    private val mainFragment by lazy { MainFragment.newInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        openFragment(mainFragment, false, false)
    }

    override fun pushFragment(fragment: Fragment) {
        openFragment(fragment, false, true)
    }

    override fun onBackPress() {
        onBackPressed()
    }
}
