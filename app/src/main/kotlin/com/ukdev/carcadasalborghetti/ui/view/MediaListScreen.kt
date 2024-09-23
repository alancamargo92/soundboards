package com.ukdev.carcadasalborghetti.ui.view

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Card
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
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
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
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.height_card_view)),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(
                                R.string.title_format,
                                index + 1,
                                media.title
                            )
                        )
                        Icon(
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
                title = "Audio 1",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Audio 2",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Audio 3",
                type = UiMediaType.AUDIO
            ),
            UiMedia(
                uri = Uri.EMPTY,
                title = "Audio 4",
                type = UiMediaType.AUDIO
            )
        ),
        onItemClicked = {},
        onItemLongClicked = {},
        isPlayingAudio = true,
        onFabClicked = {}
    )
}
