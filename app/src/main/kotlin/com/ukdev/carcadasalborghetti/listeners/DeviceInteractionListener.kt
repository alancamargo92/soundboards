package com.ukdev.carcadasalborghetti.listeners

interface DeviceInteractionListener {

    /**
     * Function called when the back button is pressed
     * @return true if super.onBackPressed can be called
     */
    fun onBackPressed(): Boolean
    fun onScreenOrientationChangedToPortrait()
    fun onScreenOrientationChangedToLandscape()

}