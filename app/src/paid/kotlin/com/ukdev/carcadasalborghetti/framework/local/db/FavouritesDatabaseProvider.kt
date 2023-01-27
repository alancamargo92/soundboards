package com.ukdev.carcadasalborghetti.framework.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukdev.carcadasalborghetti.domain.entities.Media

@Database(entities = [Media::class], version = 1, exportSchema = false)
abstract class FavouritesDatabaseProvider : RoomDatabase() {

    abstract fun provideDatabase(): FavouritesDatabase
}