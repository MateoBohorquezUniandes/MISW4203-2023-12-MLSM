package co.edu.uniandes.misw4203.group18.backvynils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.AlbumsDao
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.ArtistsDao
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.CollectorsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Track

@Database(
    entities = [Album::class, Track::class, Artist::class, Collector::class],
    version = 1,
    exportSchema = false
)
abstract class VinylRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao
    abstract fun artistsDao(): ArtistsDao
    abstract fun collectorsDao(): CollectorsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}