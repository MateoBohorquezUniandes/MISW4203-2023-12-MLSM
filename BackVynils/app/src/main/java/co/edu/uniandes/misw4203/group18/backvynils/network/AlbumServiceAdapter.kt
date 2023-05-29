package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks
import co.edu.uniandes.misw4203.group18.backvynils.models.Track
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getAlbums(context: Context) = suspendCoroutine<List<Album>> { cont ->
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
                cont.resume(list)
            },
            { cont.resumeWithException(it) }
        )
    }

    suspend fun getAlbum(context: Context, albumId: Int) =
        suspendCoroutine<AlbumWithTracks> { cont ->
            VolleyServiceBroker.getInstance(context).getRequest(
                "${albumPath}/${albumId}",
                { response ->
                    val resp = JSONObject(response)
                    val album = Album(
                        albumId = resp.getInt("id"),
                        name = resp.getString("name"),
                        cover = resp.getString("cover"),
                        recordLabel = resp.getString("recordLabel"),
                        releaseDate = resp.getString("releaseDate"),
                        genre = resp.getString("genre"),
                        description = resp.getString("description")
                    )

                    val trackList = resp.getJSONArray("tracks")
                    val tracks = mutableListOf<Track>()
                    for (i in 0 until trackList.length()) {
                        val item = trackList.getJSONObject(i)
                        tracks.add(
                            i,
                            Track(
                                trackId = item.getInt("id"),
                                fkAlbumId = album.albumId,
                                name = item.getString("name"),
                                duration = item.getString("duration"),
                            ),
                        )
                    }

                    cont.resume(AlbumWithTracks(album = album, tracks = tracks))
                },
                { cont.resumeWithException(it) }
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