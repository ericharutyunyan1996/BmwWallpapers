package com.erikharutyunyan.bmwwallpapers.Interfaces

import androidx.fragment.app.Fragment

interface OnFragmentInteraction {
    fun pushFragment(fragment: Fragment)
    fun onBackPress()
}