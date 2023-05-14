package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.ArtistsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.network.ArtistServiceAdapter

import com.android.volley.VolleyError

class ArtistRepository (private val application: Application, private val artistsDao: ArtistsDao){
    fun updateBandData(onCompleted: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        ArtistServiceAdapter.getInstance().getBands(application, { onCompleted(it) }, onError)
    }

    suspend fun updateMusicianData() : List<Artist>{
        var cached = artistsDao.getArtists()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()

            } else ArtistServiceAdapter.getInstance().getMusicians(application)
        } else cached
    }
}