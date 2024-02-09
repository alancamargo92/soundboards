package com.ukdev.carcadasalborghetti.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Media")
data class DbMedia(
    @PrimaryKey val id: String,
    val title: String,
    val typeString: String
)
