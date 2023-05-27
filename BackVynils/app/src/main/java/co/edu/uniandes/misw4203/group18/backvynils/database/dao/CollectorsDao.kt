package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: Collector)

    @Query("SELECT * FROM collectors_table WHERE collectorId = :id")
    fun getSingleCollector(id: Int): Collector?
    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll()
}