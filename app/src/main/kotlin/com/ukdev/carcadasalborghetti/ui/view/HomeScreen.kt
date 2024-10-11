package com.ukdev.carcadasalborghetti.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ukdev.carcadasalborghetti.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    fragmentManager: FragmentManager?,
    fragments: List<Fragment>,
    adView: (@Composable ColumnScope.() -> Unit)?
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
        Column(modifier = Modifier.padding(innerPadding)) {
            fragmentManager?.let {
                FragmentPager(it, fragments)
            }

            adView?.let {
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.margin_huge))
                )
                it.invoke(this)
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
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.margin_default)
            )
        )
        Text(text = stringResource(id = textRes))
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.margin_default)
            )
        )
    }
}

@Composable
@Preview
private fun PreviewHomeScreen() {
    HomeScreen(
        fragmentManager = null,
        fragments = emptyList(),
        adView = null
    )
}
