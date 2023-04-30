package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import com.android.volley.VolleyError
import org.json.JSONArray

class ArtistServiceAdapter constructor() {
    companion object {
        private var instance: ArtistServiceAdapter? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ArtistServiceAdapter().also {
                    instance = it
                }
            }
    }

    private val bandPath: String = "bands"
    private val musicianPath: String = "musicians"

    fun getBands(
        context: Context,
        onComplete: (resp: List<Artist>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            bandPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Artist(
                            artistId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("creationDate")
                        )
                    )
                }

                onComplete(list)
            },
            { onError(it) }
        )
    }

    fun getMusicians(
        context: Context,
        onComplete: (resp: List<Artist>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            musicianPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Artist(
                            artistId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("birthDate")
                        )
                    )
                }

                onComplete(list)
            },
            { onError(it) }
        )
    }
}