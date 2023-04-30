package co.edu.uniandes.misw4203.group18.backvynils.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

data class Artist(
     val artistId: Int,
     val name: String,
     val image: String,
     val description: String,
     val date: String
){

     companion object {
          const val NAME_LIMIT: Int = 25
          const val NAME_CROP: Int = 23
          @SuppressLint("SimpleDateFormat")
          private val zonedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          @SuppressLint("SimpleDateFormat")
          private val desiredFormat = SimpleDateFormat("yyyy-mm-dd")
     }

     val title: String
          get() {
               if (name.isNotBlank() && name.length > NAME_LIMIT) {
                    return name.take(NAME_CROP) + "..."
               }
               return name
          }

     val parsedDate: String?
          get() {
               if (date.isNotBlank()) {
                    return zonedFormat.parse(date)?.let { desiredFormat.format(it) }
               }
               return date
          }
}