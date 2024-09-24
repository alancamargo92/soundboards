package com.ukdev.carcadasalborghetti.navigation

import android.content.Context
import com.ukdev.carcadasalborghetti.ui.model.UiMedia

interface VideoActivityNavigation {

    fun startActivity(context: Context, media: UiMedia)
}
