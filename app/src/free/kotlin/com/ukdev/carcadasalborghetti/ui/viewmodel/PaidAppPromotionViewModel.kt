package com.ukdev.carcadasalborghetti.ui.viewmodel

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAID_VERSION_DOWNLOAD_URL = "https://play.google.com/store/apps/details?id=com.ukdev.carcadasalborghetti.paid"

@HiltViewModel
class PaidAppPromotionViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<PaidAppPromotionUiAction>()

    val action = _action.asSharedFlow()

    fun onGetPaidVersionClicked() = viewModelScope.launch(dispatcher) {
        val uri = PAID_VERSION_DOWNLOAD_URL.toUri()
        val action = PaidAppPromotionUiAction.OpenWebPage(uri)
        _action.emit(action)
    }
}
