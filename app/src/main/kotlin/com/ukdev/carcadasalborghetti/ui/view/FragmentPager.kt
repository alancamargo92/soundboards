package com.ukdev.carcadasalborghetti.ui.view

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
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
import androidx.fragment.app.commit

@Composable
fun FragmentPager(
    pagerState: PagerState,
    fragmentManager: FragmentManager,
    fragments: List<Fragment>
) {
    HorizontalPager(state = pagerState) { pageIndex ->
        val containerId = rememberSaveable { mutableIntStateOf(View.generateViewId()) }
        FragmentContainer(containerId, fragmentManager) {
            fragmentManager.commit {
                val fragment = fragments[pageIndex]
                detach(fragment)
                attach(fragment)
                replace(containerId.intValue, fragment)
            }
        }
    }
}

@Composable
private fun FragmentContainer(
    containerId: IntState,
    fragmentManager: FragmentManager,
    fragmentBlock: () -> Unit
) {
    var isInitialised by rememberSaveable { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            FragmentContainerView(context).apply {
                id = containerId.intValue
            }
        },
        update = { view ->
            if (isInitialised) {
                fragmentManager.onContainerAvailable(view)
                isInitialised = true
            } else {
                fragmentBlock()
            }
        }
    )
}
