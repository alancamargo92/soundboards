package com.ukdev.carcadasalborghetti.fragments

import androidx.fragment.app.Fragment
import com.ukdev.carcadasalborghetti.handlers.MediaHandler
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaListFragment<T: Media> : Fragment() {

    abstract val mediaHandler: MediaHandler<T>

}