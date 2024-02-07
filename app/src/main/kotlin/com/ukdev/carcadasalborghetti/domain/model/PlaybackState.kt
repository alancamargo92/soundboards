package com.ukdev.carcadasalborghetti.domain.model

data class PlaybackState(val isPlaying: Boolean = false) {

    fun onPlaying() = copy(isPlaying = true)

    fun onFinishedPlaying() = copy(isPlaying = false)
}
