package co.edu.uniandes.misw4203.group18.backvynils.database.dao

import androidx.room.Dao
import androidx.room.Query
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>
}