package co.edu.uniandes.misw4203.group18.backvynils.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

data class Album(
    val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
) {

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

    val parsedReleaseDate: String?
        get() {
            if (releaseDate.isNotBlank()) {
                return zonedFormat.parse(releaseDate)?.let { desiredFormat.format(it) }
            }
            return releaseDate
        }
}
