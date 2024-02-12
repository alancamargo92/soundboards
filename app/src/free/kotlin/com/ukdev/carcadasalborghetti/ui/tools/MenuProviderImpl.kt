package com.ukdev.carcadasalborghetti.ui.tools

import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.ui.model.MenuItemActionPair
import javax.inject.Inject

class MenuProviderImpl @Inject constructor(
    dialogueHelper: DialogueHelper
) : MenuProvider(dialogueHelper) {

    override fun getMenuItemsAndActions(): List<MenuItemActionPair> {
        return defaultItemsAndActions
    }
}
