package com.ukdev.carcadasalborghetti.ui.tools

import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import javax.inject.Inject

class MenuProviderImpl @Inject constructor(
    dialogueHelper: DialogueHelper
) : MenuProvider(dialogueHelper) {

    override fun getMenuItemsAndActions(): List<MenuItemActionPair> {
        return defaultItemsAndActions
    }
}
