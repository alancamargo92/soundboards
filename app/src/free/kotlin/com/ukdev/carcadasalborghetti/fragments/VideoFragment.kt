package com.ukdev.carcadasalborghetti.fragments

import androidx.fragment.app.Fragment
import com.ukdev.carcadasalborghetti.listeners.DeviceInteractionListener

class VideoFragment : Fragment(), DeviceInteractionListener {

    override fun onBackPressed() = true

    override fun onScreenOrientationChangedToPortrait() { }

    override fun onScreenOrientationChangedToLandscape() { }
}