package com.ukdev.carcadasalborghetti.ui.view

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MediaListScreen(
    items: List<UiMedia>,
    onItemClicked: (UiMedia) -> Unit,
    onItemLongClicked: (UiMedia) -> Unit,
    isPlayingAudio: Boolean,
    onFabClicked: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (isPlayingAudio) {
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
        isPlayingAudio = true,
        onFabClicked = {}
    )
}
