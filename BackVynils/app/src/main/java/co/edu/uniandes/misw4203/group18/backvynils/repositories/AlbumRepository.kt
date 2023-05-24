package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.AlbumsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Track
import co.edu.uniandes.misw4203.group18.backvynils.network.AlbumServiceAdapter
import com.android.volley.VolleyError

class AlbumRepository(private val application: Application, private val albumsDao: AlbumsDao) {
    suspend fun updateAlbumData(): List<Album> {

        if (isNetworkConnectivityError()) {
            val cachedAlbums = albumsDao.getAlbums()
            if (cachedAlbums.isEmpty()) {
                throw Exception("No data has been stored locally")
            }
            return cachedAlbums
        }
        return AlbumServiceAdapter.getInstance().getAlbums(application)
    }

    suspend fun insertAlbums(albums: List<Album>) {
        for (album in albums) {
            albumsDao.insert(album)
        }
    }

    fun isNetworkConnectivityError(): Boolean {
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE
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
        AlbumServiceAdapter.getInstance()
            .postTrackToAlbum(application, albumId, track, onCompleted, onError)
    }

}