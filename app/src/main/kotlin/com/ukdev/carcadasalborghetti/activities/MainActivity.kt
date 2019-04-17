package com.ukdev.carcadasalborghetti.activities

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.CarcadaAdapter
import com.ukdev.carcadasalborghetti.listeners.AudioCallback
import com.ukdev.carcadasalborghetti.listeners.QueryListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Carcada
import com.ukdev.carcadasalborghetti.privacy.PrivacyTermsDialogue
import com.ukdev.carcadasalborghetti.utils.*
import com.ukdev.carcadasalborghetti.viewmodel.CarcadaViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewInteractionListener, AudioCallback {

    private val viewModel by provideViewModel(CarcadaViewModel::class)
    private val adapter = CarcadaAdapter()
    private val layoutManager = GridLayoutManager(this, 3)
    private val audioHandler = AudioHandler(this)

    private var topPosition = 0
    private var carcadas = listOf<Carcada>()
    private var carcadaToShare: Carcada? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureRecyclerView()
        fab.setOnClickListener { audioHandler.stop(callback = this) }
        viewModel.getCarcadas().observe(this, Observer { carcadas ->
            this.carcadas = carcadas
            adapter.setData(carcadas)
        })
        ad_view.loadAd(AdRequest.Builder().build())
    }

    override fun onBackPressed() {
        if (topPosition == 0)
            super.onBackPressed()
        else
            recycler_view.smoothScrollToPosition(0)
    }

    override fun onPause() {
        super.onPause()
        audioHandler.stop(callback = this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.run {
            (findItem(R.id.item_search)?.actionView as SearchView).run {
                setOnQueryTextListener(QueryListener(adapter, carcadas))
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_privacy -> showPrivacyPolicy()
            R.id.item_about -> showAppInfo()
            else -> false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PERMISSION_GRANTED }

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS == permissionsGranted)
            carcadaToShare?.let(audioHandler::share)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        layoutManager.spanCount = when (newConfig.orientation) {
            ORIENTATION_PORTRAIT -> 3
            else -> 4
        }
    }

    override fun onItemClick(carcada: Carcada) {
        audioHandler.play(carcada.audioFileRes, callback = this)
    }

    override fun onItemLongClick(carcada: Carcada) {
        if (SDK_INT >= M && !hasStoragePermissions()) {
            carcadaToShare = carcada
            requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSIONS)
        } else {
            audioHandler.share(carcada)
        }
    }

    override fun onStartPlayback() {
        fab.visibility = VISIBLE
    }

    override fun onStopPlayback() {
        fab.visibility = GONE
    }

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter.apply { setListener(this@MainActivity) }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        })
    }

    private fun showPrivacyPolicy(): Boolean {
        PrivacyTermsDialogue().show(supportFragmentManager, PrivacyTermsDialogue.TAG)
        return true
    }

    private fun showAppInfo(): Boolean {
        val title = getString(R.string.app_info, getAppName(), getAppVersion())
        AlertDialog.Builder(this).setTitle(title)
                .setMessage(R.string.developer_info)
                .setNeutralButton(R.string.ok, null)
                .setIcon(R.mipmap.ic_launcher)
                .show()
        return true
    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 123
    }

}