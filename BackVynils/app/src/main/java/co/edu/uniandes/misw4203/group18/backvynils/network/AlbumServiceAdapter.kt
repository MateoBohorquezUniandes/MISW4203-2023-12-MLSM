package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Album.Track
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

class AlbumServiceAdapter constructor() {
    companion object {
        private var instance: AlbumServiceAdapter? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: AlbumServiceAdapter().also {
                    instance = it
                }
            }
    }

    private val albumPath: String = "albums"

    fun getAlbums(
        context: Context,
        onComplete: (resp: List<Album>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            albumPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Album(
                            albumId = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description")
                        )
                    )
                }

                onComplete(list)
            },
            { onError(it) }
        )
    }
    fun postAlbum(
            context: Context,
            album: Album,
            onComplete: () -> Unit,
            onError: (error: VolleyError) -> Unit
        ) {
            val jsonObject = JSONObject()
            jsonObject.put("name", album.name)
            jsonObject.put("cover", album.cover)
            jsonObject.put("recordLabel", album.recordLabel)
            jsonObject.put("releaseDate", album.releaseDate)
            jsonObject.put("genre", album.genre)
            jsonObject.put("description", album.description)

            VolleyServiceBroker.getInstance(context).postRequest(
                albumPath,
                jsonObject,
                { onComplete() },
                { onError(it) }
            )
        }
    fun postTrackToAlbum(
        context: Context,
        albumId: Int,
        track: Track,
        onComplete: () -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("name", track.name)
        jsonObject.put("duration", track.duration)

        val url = "$albumPath/$albumId/tracks"

        VolleyServiceBroker.getInstance(context).postRequest(
            url,
            jsonObject,
            { onComplete() },
            { onError(it) }
        )
    }
}