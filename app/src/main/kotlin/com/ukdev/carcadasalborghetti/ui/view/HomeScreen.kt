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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.model.MediaListTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tabs: List<MediaListTab>,
    pagerState: PagerState,
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
            val coroutineScope = rememberCoroutineScope()
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = colorResource(id = R.color.black)
            ) {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = colorResource(id = R.color.black),
                    indicator = {},
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, tab ->
                        MediaTab(
                            isSelected = pagerState.currentPage == index,
                            textRes = tab.textRes,
                            iconRes = tab.iconRes,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            fragmentManager?.let {
                FragmentPager(pagerState, it, fragments)
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
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Tab(
        selected = isSelected,
        selectedContentColor = colorResource(id = R.color.red),
        unselectedContentColor = colorResource(id = R.color.white),
        onClick = onClick
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
        tabs = listOf(
            MediaListTab.AUDIOS,
            MediaListTab.VIDEOS,
            MediaListTab.FAVOURITES
        ),
        pagerState = rememberPagerState { 3 },
        fragmentManager = null,
        fragments = emptyList(),
        adView = null
    )
}
