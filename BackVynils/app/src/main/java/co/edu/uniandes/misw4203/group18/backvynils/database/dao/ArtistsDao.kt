package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists_table")
    fun getArtists():List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: Artist)
}