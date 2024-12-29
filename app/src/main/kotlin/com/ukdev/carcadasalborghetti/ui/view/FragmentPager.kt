package com.ukdev.carcadasalborghetti.ui.view

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow

@Composable
fun FragmentPager(
    pagerState: PagerState,
    fragmentManager: FragmentManager,
    fragments: List<Fragment>
) {
    HorizontalPager(state = pagerState) { pageIndex ->
        val containerId = rememberSaveable { mutableIntStateOf(View.generateViewId()) }
        FragmentContainer(containerId.intValue) {
            fragmentManager.commitNow {
                val fragment = fragments[pageIndex]
                replace(containerId.intValue, fragment)
            }
        }
    }
}

@Composable
private fun FragmentContainer(
    containerId: Int,
    fragmentBlock: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            FragmentContainerView(context).apply {
                id = containerId
            }
        },
        update = {
            fragmentBlock()
        }
    )
}
