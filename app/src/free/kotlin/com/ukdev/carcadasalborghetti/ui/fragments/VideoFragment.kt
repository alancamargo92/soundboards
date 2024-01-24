package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.FragmentVideoBinding

open class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding: FragmentVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.txtPaidVersion.text = getString(R.string.get_paid_version_rationale)
        binding.btPaidVersion.setOnClickListener {
            showPaidVersion()
        }
    }

    private fun showPaidVersion() {
        val url = "https://play.google.com/store/apps/details?id=com.ukdev.carcadasalborghetti.paid"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
