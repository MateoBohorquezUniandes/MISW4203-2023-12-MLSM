package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.*
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>

    @Transaction
    @Query("SELECT * FROM albums_table WHERE albumId = :albumId ORDER BY name")
    fun getAlbumWithTracks(albumId: Int): List<AlbumWithTracks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album)
}