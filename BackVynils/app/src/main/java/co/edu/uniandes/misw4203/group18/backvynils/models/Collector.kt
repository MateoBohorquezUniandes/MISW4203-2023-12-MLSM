package co.edu.uniandes.misw4203.group18.backvynils.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors_table")
data class Collector(
   @PrimaryKey val collectorId: Int,
   val name: String
) {
}