package co.edu.uniandes.misw4203.group18.backvynils.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "artists_table")
data class Artist(
     @PrimaryKey val artistId: Int,
     val name: String,
     val date: String,
     val description: String,
     val image: String
){

     companion object {
          const val NAME_LIMIT: Int = 25
          const val NAME_CROP: Int = 23
     }

     val title: String
          get() {
               if (name.isNotBlank() && name.length > NAME_LIMIT) {
                    return name.take(NAME_CROP) + "..."
               }
               return name
          }
}