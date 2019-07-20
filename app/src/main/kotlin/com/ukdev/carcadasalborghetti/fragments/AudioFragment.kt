package com.ukdev.carcadasalborghetti.fragments

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.listeners.AudioCallback
import com.ukdev.carcadasalborghetti.listeners.QueryListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.utils.AudioHandler
import com.ukdev.carcadasalborghetti.utils.hasStoragePermissions
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.utils.requestStoragePermissions
import com.ukdev.carcadasalborghetti.viewmodel.AudioViewModel
import kotlinx.android.synthetic.main.layout_list.*

class AudioFragment(
        private val audioHandler: AudioHandler
) : Fragment(), RecyclerViewInteractionListener {

    private val viewModel by provideViewModel(AudioViewModel::class)
    private var audios = listOf<Audio>()
    private val adapter = AudioAdapter()
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 3) }

    private var topPosition = 0
    private var audioToShare: Audio? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        viewModel.getAudios().observe(this, Observer { audios ->
            this.audios = audios
            adapter.setData(audios)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.run {
            (findItem(R.id.item_search)?.actionView as SearchView).run {
                setOnQueryTextListener(QueryListener(adapter, audios))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS == permissionsGranted)
            audioToShare?.let(audioHandler::share)
    }

    override fun onItemClick(audio: Audio) {
        audioHandler.play(audio.fileRes, callback = (requireActivity() as AudioCallback))
    }

    override fun onItemLongClick(audio: Audio) {
        if (SDK_INT >= M && !hasStoragePermissions()) {
            audioToShare = audio
            requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSIONS)
        } else {
            audioHandler.share(audio)
        }
    }

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter.apply { setListener(this@AudioFragment) }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        })
    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 123
    }

}