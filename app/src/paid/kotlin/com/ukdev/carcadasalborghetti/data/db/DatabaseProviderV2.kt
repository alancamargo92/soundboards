package com.ukdev.carcadasalborghetti.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukdev.carcadasalborghetti.data.model.DbMedia

@Database(
    entities = [DbMedia::class],
    version = 2,
    exportSchema = false
)
abstract class DatabaseProviderV2 : RoomDatabase() {

    abstract fun getFavouritesDao(): FavouritesDaoV2
}
