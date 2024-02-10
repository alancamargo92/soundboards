package com.ukdev.carcadasalborghetti.ui.viewmodel.home

import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment

data class HomeUiState(val fragmentMap: Map<Int, MediaListFragment>? = null) {

    fun onFragmentMapReceived(
        fragmentMap: Map<Int, MediaListFragment>
    ) = copy(fragmentMap = fragmentMap)
}
