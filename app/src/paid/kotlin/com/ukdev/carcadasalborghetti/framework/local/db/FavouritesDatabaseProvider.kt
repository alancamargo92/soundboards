package com.ukdev.carcadasalborghetti.framework.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ukdev.carcadasalborghetti.domain.entities.Media

@Database(entities = [Media::class], version = 1, exportSchema = false)
abstract class FavouritesDatabaseProvider : RoomDatabase() {

    abstract fun provideDatabase(): FavouritesDatabase

    companion object {
        private var instance: FavouritesDatabaseProvider? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): FavouritesDatabaseProvider {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context,
                        FavouritesDatabaseProvider::class.java,
                        "favourites_database"
                ).fallbackToDestructiveMigration().build()
            }

            return instance!!
        }
    }

}