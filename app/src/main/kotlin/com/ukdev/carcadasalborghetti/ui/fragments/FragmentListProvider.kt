package com.ukdev.carcadasalborghetti.ui.fragments

import androidx.fragment.app.Fragment

interface FragmentListProvider {

    fun provideFragmentList(): List<Fragment>
}
