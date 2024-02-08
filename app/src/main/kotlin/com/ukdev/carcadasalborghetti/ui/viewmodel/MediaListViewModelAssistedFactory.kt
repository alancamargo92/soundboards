package com.ukdev.carcadasalborghetti.ui.viewmodel

import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MediaListViewModelAssistedFactory {

    fun create(fragmentType: MediaListFragmentType): MediaListViewModel
}