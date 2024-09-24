package com.ukdev.carcadasalborghetti.ui.view

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

@Composable
fun FragmentPager(
    fragmentManager: FragmentManager,
    fragments: List<Fragment>
) {
    val pagerState = rememberPagerState(
        pageCount = { fragments.size }
    )

    HorizontalPager(state = pagerState) { pageIndex ->
        FragmentContainer(fragmentManager) { containerId ->
            replace(containerId, fragments[pageIndex])
        }
    }
}

@Composable
private fun FragmentContainer(
    fragmentManager: FragmentManager,
    fragmentBlock: FragmentTransaction.(Int) -> Unit
) {
    val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }
    var isInitialised by rememberSaveable { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            FragmentContainerView(context).apply {
                id = containerId
            }
        },
        update = { view ->
            if (isInitialised) {
                fragmentManager.onContainerAvailable(view)
                isInitialised = true
            } else {
                fragmentManager.commit { fragmentBlock(containerId) }
            }
        }
    )
}
