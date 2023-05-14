package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.group18.backvynils.models.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)
}