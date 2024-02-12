package com.ukdev.carcadasalborghetti.testtools

import com.ukdev.carcadasalborghetti.data.mapping.toDb

fun stubDbMediaList() = listOf(stubMedia().toDb())
