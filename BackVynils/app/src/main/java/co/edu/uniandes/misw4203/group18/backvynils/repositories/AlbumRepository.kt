package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.AlbumsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks
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

    suspend fun updateDetailAlbumData(albumId: Int): AlbumWithTracks {
        if (isNetworkConnectivityError()) {
            val cachedAlbum = albumsDao.getAlbumWithTracks(albumId)
            if (cachedAlbum.isEmpty()) {
                throw Exception("No data has been stored locally")
            }
            return cachedAlbum.first()
        }
        return AlbumServiceAdapter.getInstance().getAlbum(application, albumId)
    }

    suspend fun insertAlbums(albums: List<Album>) {
        albumsDao.insertAlbums(albums)
    }

    suspend fun insertAlbumWithTracks(albumWithTracks: AlbumWithTracks) {
        albumsDao.insertAlbum(albumWithTracks.album)
        if (!albumWithTracks.tracks.isEmpty()) {
            Log.d("AlbumRepository", "Storing tracks")
            albumsDao.insertTracks(albumWithTracks.tracks)
            Log.d("AlbumRepository", "Storing tracks completed")
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