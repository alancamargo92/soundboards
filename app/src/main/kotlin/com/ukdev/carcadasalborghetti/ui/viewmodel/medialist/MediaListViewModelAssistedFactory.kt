package com.ukdev.carcadasalborghetti.ui.viewmodel.medialist

import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MediaListViewModelAssistedFactory {

    fun create(fragmentType: MediaListFragmentType): MediaListViewModel
}