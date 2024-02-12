package com.ukdev.carcadasalborghetti.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.core.tools.PreferencesManager
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<HomeUiAction>()

    val action = _action.asSharedFlow()

    fun start() {
        if (preferencesManager.shouldShowTip()) {
            val action = HomeUiAction.ShowTip
            sendAction(action)
        }
    }

    fun onDoNotShowTipAgainClicked() {
        preferencesManager.disableTip()
    }

    private fun sendAction(action: HomeUiAction) {
        viewModelScope.launch(dispatcher) {
            _action.emit(action)
        }
    }
}
