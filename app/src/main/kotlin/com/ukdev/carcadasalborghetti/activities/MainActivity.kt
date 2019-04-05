package com.ukdev.carcadasalborghetti.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.CarcadaAdapter
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.CarcadaViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter.apply {
            setOnItemClickListener { carcada ->
                // TODO: play audio
            }
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        })
    }

}