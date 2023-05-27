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
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()

            } else ArtistServiceAdapter.getInstance().getMusicians(application)
        } else cached
    }

    suspend fun getAnArtist(id: Int) : Artist {
        val cached = artistsDao.getSingleArtist(id)
        return if(cached == null){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Artist(0,"","0000-00-00T00:00:00.000Z","","")
            } else ArtistServiceAdapter.getInstance().getSingleArtist(application, id)
        } else cached
    }
}