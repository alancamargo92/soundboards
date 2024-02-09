package com.ukdev.carcadasalborghetti.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukdev.carcadasalborghetti.data.model.DbMedia

@Database(
    entities = [DbMedia::class],
    version = 3,
    exportSchema = false
)
abstract class DatabaseProvider : RoomDatabase() {

    abstract fun getFavouritesDao(): FavouritesDao
}
