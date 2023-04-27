package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.models.Band
import co.edu.uniandes.misw4203.group18.backvynils.models.Musician
import co.edu.uniandes.misw4203.group18.backvynils.network.PerformerServiceAdapter

import com.android.volley.VolleyError

class PerformerRepository (private val application: Application){
    fun updateBandData(onCompleted: (List<Band>)->Unit, onError: (VolleyError)->Unit) {
        PerformerServiceAdapter.getInstance().getBands(application, { onCompleted(it) }, onError)
    }

    fun updateMusicianData(onCompleted: (List<Musician>)->Unit, onError: (VolleyError)->Unit) {
        PerformerServiceAdapter.getInstance().getMusicians(application, { onCompleted(it) }, onError)
    }
}