package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.ArtistsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.network.ArtistServiceAdapter


class ArtistRepository (private val application: Application, private val artistsDao: ArtistsDao){

    suspend fun updateMusicianData() : List<Artist>{
        val cached = artistsDao.getArtists()
        return cached.ifEmpty {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()

            } else ArtistServiceAdapter.getInstance().getMusicians(application)
        }
    }

    suspend fun insertArtists(artists: List<Artist>) {
        for (artist in artists) {
            artistsDao.insert(artist)
        }
    }
}