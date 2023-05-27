package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.*
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks
import co.edu.uniandes.misw4203.group18.backvynils.models.Track

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table  ORDER BY name")
    fun getAlbums(): List<Album>

    @Transaction
    @Query("SELECT * FROM albums_table WHERE albumId = :albumId")
    fun getAlbumWithTracks(albumId: Int): List<AlbumWithTracks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<Track>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

}