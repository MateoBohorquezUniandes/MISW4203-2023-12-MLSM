package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.network.ArtistServiceAdapter

import com.android.volley.VolleyError

class ArtistRepository (private val application: Application){
    fun updateBandData(onCompleted: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        ArtistServiceAdapter.getInstance().getBands(application, { onCompleted(it) }, onError)
    }

    suspend fun updateMusicianData() : List<Artist>{
        return ArtistServiceAdapter.getInstance().getMusicians(application)
    }
}