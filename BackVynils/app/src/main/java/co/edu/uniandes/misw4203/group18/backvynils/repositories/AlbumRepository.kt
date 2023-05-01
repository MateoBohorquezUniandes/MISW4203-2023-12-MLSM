package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.network.AlbumServiceAdapter
import com.android.volley.VolleyError

class AlbumRepository (private val application: Application){
    fun updateAlbumData(onCompleted: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        AlbumServiceAdapter.getInstance().getAlbums(application, { onCompleted(it) }, onError)
    }
    fun postAlbum(
        album: Album,
        onCompleted: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        AlbumServiceAdapter.getInstance().postAlbum(application, album, onCompleted, onError)
    }
}