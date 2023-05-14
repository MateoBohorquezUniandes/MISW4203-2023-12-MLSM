package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Album.Track
import co.edu.uniandes.misw4203.group18.backvynils.network.AlbumServiceAdapter
import com.android.volley.VolleyError

class AlbumRepository (private val application: Application){
    suspend fun updateAlbumData(): List<Album> {
        return AlbumServiceAdapter.getInstance().getAlbums(application)
    }
    fun postAlbum(
        album: Album,
        onCompleted: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        AlbumServiceAdapter.getInstance().postAlbum(application, album, onCompleted, onError)
    }
    fun postTrackToAlbum(
        albumId: Int,
        track: Track,
        onCompleted: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        AlbumServiceAdapter.getInstance().postTrackToAlbum(application, albumId, track, onCompleted, onError)
    }

}