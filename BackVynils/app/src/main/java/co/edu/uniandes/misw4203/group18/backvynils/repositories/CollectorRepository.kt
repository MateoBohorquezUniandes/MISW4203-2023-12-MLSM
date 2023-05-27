package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.CollectorsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.network.CollectorServiceAdapter
class CollectorRepository(private val application: Application, private val collectorsDao: CollectorsDao) {

    /*fun updateCollectorData(onCompleted: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        CollectorServiceAdapter.getInstance().getCollectors(application,{onCompleted(it)}, onError)
        //CollectorServiceAdapter.getInstance().getCollectorsOffline({onCompleted(it)})
    }*/

    suspend fun refreshCollectorData(): List<Collector> {
        var cached = collectorsDao.getCollectors()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else CollectorServiceAdapter.getInstance().getCollectors(application)
        } else cached
    }

    /*fun updateCollectorDetailData(id:String,onCompleted: (Collector) -> Unit, onError: (VolleyError) -> Unit){
        CollectorServiceAdapter.getInstance().getSingleCollector(id,application,{onCompleted(it)}, onError)
    }*/

    suspend fun refreshCollectorDetailData(searchId: Int): Collector {
        var cached = collectorsDao.getSingleCollector(searchId)
        return if(cached == null){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Collector(0,"","","",0,0)
            } else CollectorServiceAdapter.getInstance().getSingleCollector(searchId.toString(),application)
        } else cached
    }
}