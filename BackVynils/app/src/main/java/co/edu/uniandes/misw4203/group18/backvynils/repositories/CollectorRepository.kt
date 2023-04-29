package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.network.CollectorServiceAdapter
import com.android.volley.VolleyError
class CollectorRepository(private val application: Application) {

    fun updateCollectorData(onCompleted: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        //CollectorServiceAdapter.getInstance().getCollectors(application,{onCompleted(it)}, onError)
        CollectorServiceAdapter.getInstance().getCollectorsOffline({onCompleted(it)})
    }
}