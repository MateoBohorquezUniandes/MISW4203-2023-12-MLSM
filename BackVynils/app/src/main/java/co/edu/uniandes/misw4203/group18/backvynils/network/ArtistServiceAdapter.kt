package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ArtistServiceAdapter  {
    companion object {
        private var instance: ArtistServiceAdapter? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ArtistServiceAdapter().also {
                    instance = it
                }
            }
    }

    private val musicianPath: String = "musicians"

    suspend fun getMusicians(context: Context) = suspendCoroutine<List<Artist>> { cont ->
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
                cont.resume(list)
            },
            {
            cont.resumeWithException(it)
            })
    }

    suspend fun getSingleArtist(context: Context, id: Int) = suspendCoroutine<Artist> { cont ->
        VolleyServiceBroker.getInstance(context).getRequest(
            "$musicianPath/$id",
            { response ->
                val resp = JSONObject(response)
                val artist = Artist(
                    artistId = resp.getInt("id"),
                    name = resp.getString("name"),
                    image = resp.getString("image"),
                    description = resp.getString("description"),
                    date = resp.getString("birthDate")
                )
                cont.resume(artist)
            },
            {
                if(it.networkResponse.statusCode == 404){
                    cont.resume(Artist(1010,"No se pudo recuperar el artista","0000-00-00T00:00:00.000Z","",""))
                }
                else{
                    cont.resumeWithException(it)
                }

            })
    }
}