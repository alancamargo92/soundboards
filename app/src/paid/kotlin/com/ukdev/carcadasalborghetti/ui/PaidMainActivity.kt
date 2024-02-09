package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import com.ukdev.carcadasalborghetti.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaidMainActivity : MainActivity() {

    private var _binding: ActivityMainBinding? = null

    override val baseBinding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        super.onCreate(savedInstanceState)
    }
}
