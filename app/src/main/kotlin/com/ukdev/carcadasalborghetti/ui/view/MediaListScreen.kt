package com.ukdev.carcadasalborghetti.ui.view

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun MediaListScreen(
    items: List<UiMedia>,
    onItemClicked: (UiMedia) -> Unit,
    onItemLongClicked: (UiMedia) -> Unit,
    showStopButton: Boolean,
    onFabClicked: () -> Unit,
    showProgressBar: Boolean,
    error: UiError?,
    onTryAgainClicked: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean
) {
    Scaffold(
        floatingActionButton = {
            if (showStopButton) {
                FloatingActionButton(
                    onClick = onFabClicked,
                    containerColor = colorResource(R.color.red)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_stop),
                        contentDescription = stringResource(R.string.stop),
                        tint = colorResource(R.color.white)
                    )
                }
            }
        }
    ) { innerPadding ->
        when {
            showProgressBar -> {
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colorResource(R.color.red)
                    )
                }
            }

            error != null -> {
                Column(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        painter = painterResource(error.iconRes),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_default)))
                    Text(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_default))
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = stringResource(error.textRes)
                    )

                    if (error != UiError.NO_FAVOURITES) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_large)))
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            colors = ButtonColors(
                                containerColor = colorResource(R.color.red),
                                contentColor = colorResource(R.color.white),
                                disabledContainerColor = colorResource(R.color.red),
                                disabledContentColor = colorResource(R.color.white)
                            ),
                            shape = RoundedCornerShape(
                                size = dimensionResource(R.dimen.radius_button)
                            ),
                            onClick = onTryAgainClicked
                        ) {
                            Text(text = stringResource(R.string.try_again))
                        }
                    }
                }
            }

            items.isNotEmpty() -> {
                PullToRefreshBox(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = colorResource(R.color.white)),
                        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_default)),
                        columns = GridCells.Fixed(count = 3),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_default)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_default))
                    ) {
                        items(items.size) { index ->
                            val media = items[index]

                            Card(
                                modifier = Modifier.combinedClickable(
                                    onClick = { onItemClicked(media) },
                                    onLongClick = { onItemLongClicked(media) }
                                ).border(
                                    width = dimensionResource(R.dimen.width_card_stroke),
                                    color = colorResource(R.color.black),
                                    shape = RoundedCornerShape(size = dimensionResource(R.dimen.radius_card))
                                ),
                                colors = CardColors(
                                    containerColor = colorResource(R.color.white),
                                    contentColor = colorResource(R.color.black),
                                    disabledContainerColor = colorResource(R.color.white),
                                    disabledContentColor = colorResource(R.color.black)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(dimensionResource(R.dimen.height_card_view))
                                        .padding(dimensionResource(R.dimen.padding_default)),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(
                                            R.string.title_format,
                                            index + 1,
                                            media.title
                                        ),
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Icon(
                                        modifier = Modifier.weight(1f),
                                        painter = painterResource(media.type.iconRes),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMediaListScreen() {
    MediaListScreen(
        items = listOf(
            UiMedia(
                uri = Uri.EMPTY,
                title = "O que tem de tubarão passando fome",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Traficante bom é traficante morto",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Praias do Paranã",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Ah Luciana vai pro inferno",
                type = UiMediaType.AUDIO
            )
        ),
        onItemClicked = {},
        onItemLongClicked = {},
        showStopButton = true,
        onFabClicked = {},
        showProgressBar = false,
        error = null,
        onTryAgainClicked = {},
        onRefresh = {},
        isRefreshing = false
    )
}
