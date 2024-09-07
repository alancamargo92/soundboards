package com.ukdev.carcadasalborghetti.ui.view

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.ukdev.carcadasalborghetti.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    fragmentManager: FragmentManager? = null,
    fragmentBlock: FragmentTransaction.() -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.black),
                    titleContentColor = colorResource(id = R.color.white)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = colorResource(id = R.color.black)
            ) {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = 0,
                    containerColor = colorResource(id = R.color.black),
                    indicator = {},
                    divider = {}
                ) {
                    MediaTab(
                        isSelected = true,
                        textRes = R.string.audios,
                        iconRes = R.drawable.ic_tab_audio
                    )
                    MediaTab(
                        isSelected = false,
                        textRes = R.string.videos,
                        iconRes = R.drawable.ic_tab_video
                    )
                    MediaTab(
                        isSelected = false,
                        textRes = R.string.favourites,
                        iconRes = R.drawable.ic_tab_favourites
                    )
                }
            }
        }
    ) { innerPadding ->
        fragmentManager?.let {
            FragmentContainer(
                modifier = Modifier.padding(innerPadding),
                fragmentManager = it
            ) {
                fragmentBlock()
            }
        }
    }
}

@Composable
private fun MediaTab(
    isSelected: Boolean,
    @StringRes textRes: Int,
    @DrawableRes iconRes: Int
) {
    Tab(
        selected = isSelected,
        selectedContentColor = colorResource(id = R.color.red),
        unselectedContentColor = colorResource(id = R.color.white),
        onClick = {  }
    ) {
        Text(text = stringResource(id = textRes))
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.margin_default)
            )
        )
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.margin_default)
            )
        )
    }
}

@Composable
private fun FragmentContainer(
    modifier: Modifier,
    fragmentManager: FragmentManager,
    fragmentBlock: FragmentTransaction.() -> Unit
) {
    val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }
    var isInitialised by rememberSaveable { mutableStateOf(false) }

    AndroidView(
        modifier = modifier.fillMaxSize(),
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
                fragmentManager.commit { fragmentBlock() }
            }
        }
    )
}

@Composable
@Preview
private fun PreviewHomeScreen() {
    HomeScreen()
}
