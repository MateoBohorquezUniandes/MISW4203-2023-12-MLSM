package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import com.android.volley.VolleyError
import org.json.JSONArray
class CollectorServiceAdapter {

    companion object {
        private var instance: CollectorServiceAdapter? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: CollectorServiceAdapter().also{
                    instance = it
                }
            }
    }

    private val collectorPath: String = "collectors"

    fun getCollectors(
        context: Context,
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            collectorPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Collector(
                            collectorId = item.getInt("id"),
                            name = item.getString("name")
                        )
                    )
                }
                onComplete(list)
            },{
                onError(it)
            }
        )
    }

    fun getCollectorsOffline(onComplete: (resp: List<Collector>) -> Unit){
        val list = mutableListOf<Collector>()
        list.add(0, Collector(0,"Alexandrey"))
        list.add(1, Collector(1,"Lara"))
        list.add(2,Collector(2,"Melissa"))
        list.add(3,Collector(3,"Santiago"))
        list.add(4,Collector(4,"Mateo"))
        onComplete(list)
    }

    fun getCollectorsEmpty(onComplete: (resp: List<Collector>) -> Unit) {
        val list = mutableListOf<Collector>()
        onComplete(list)
    }

    fun getCollectorsTen(onComplete: (resp: List<Collector>) -> Unit) {
        val list = mutableListOf<Collector>()
        list.add(0, Collector(0,"Collector 1"))
        list.add(1, Collector(1,"Collector 2"))
        list.add(2,Collector(2,"Collector 3"))
        list.add(3,Collector(3,"Collector 4"))
        list.add(4,Collector(4,"Collector 5"))
        list.add(5, Collector(5,"Collector 6"))
        list.add(6, Collector(6,"Collector 7"))
        list.add(7,Collector(7,"Collector 8"))
        list.add(8,Collector(8,"Collector 9"))
        list.add(9,Collector(9,"Collector 10"))
        onComplete(list)
    }
}