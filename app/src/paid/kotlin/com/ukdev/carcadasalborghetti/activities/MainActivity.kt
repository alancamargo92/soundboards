package com.ukdev.carcadasalborghetti.activities

import android.os.Bundle
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.VideoViewModel

class MainActivity : BaseActivity() {

    private val videoViewModel by provideViewModel(VideoViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}