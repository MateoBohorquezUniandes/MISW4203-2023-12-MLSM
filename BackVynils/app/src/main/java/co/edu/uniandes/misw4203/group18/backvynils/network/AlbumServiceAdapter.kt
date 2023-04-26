package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import com.android.volley.VolleyError
import org.json.JSONArray

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
}