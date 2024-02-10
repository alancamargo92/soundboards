package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaidHomeActivity : HomeActivity() {

    private var _binding: ActivityHomeBinding? = null

    override val baseBinding: ActivityHomeBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        super.onCreate(savedInstanceState)
    }
}
