package com.ukdev.carcadasalborghetti.ui.viewmodel

import android.net.Uri

sealed class PaidAppPromotionUiAction {

    data class OpenWebPage(val uri: Uri) : PaidAppPromotionUiAction()
}
