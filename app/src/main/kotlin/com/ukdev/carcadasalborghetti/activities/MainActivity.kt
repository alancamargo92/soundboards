package com.ukdev.carcadasalborghetti.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.CarcadaAdapter
import com.ukdev.carcadasalborghetti.adapter.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Carcada
import com.ukdev.carcadasalborghetti.utils.getAppName
import com.ukdev.carcadasalborghetti.utils.getAppVersion
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.CarcadaViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewInteractionListener {

    private val viewModel by provideViewModel(CarcadaViewModel::class)
    private val adapter = CarcadaAdapter()
    private val layoutManager = GridLayoutManager(this, 3)

    private var topPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureRecyclerView()
        viewModel.getCarcadas().observe(this, Observer { carcadas ->
            adapter.setData(carcadas)
        })
    }

    override fun onBackPressed() {
        if (topPosition == 0)
            super.onBackPressed()
        else
            recycler_view.smoothScrollToPosition(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_search -> TODO("search")
            R.id.item_youtube -> openYouTube()
            R.id.item_about -> showAppInfo()
            else -> false
        }
    }

    override fun onItemClick(carcada: Carcada) {
        // TODO: play audio
    }

    override fun onItemLongClick(carcada: Carcada) {
        // TODO: share audio
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

    private fun openYouTube(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_url)))
        startActivity(intent)
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

}