package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

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
                            name = item.getString("name"),
                            email = item.getString("email"),
                            telephone = item.getString("telephone"),
                            comments = extractListComments(item)
                        )
                    )
                }
                onComplete(list)
            },{
                onError(it)
            }
        )
    }
    fun getSingleCollector(
        id: String,
        context: Context,
        onComplete: (resp: Collector) -> Unit,
        onError: (error: VolleyError) -> Unit
    ){
        VolleyServiceBroker.getInstance(context).getRequest(
            collectorPath + "/" + id,
            {response ->
                val resp = JSONObject(response)
                val coll: Collector = Collector(
                    resp.getInt("id"),
                    resp.getString("name"),
                    resp.getString("telephone"),
                    resp.getString("email"),
                    extractListComments(resp),
                )
                onComplete(coll)
            },{
                onError(it)
            }
        )
    }

    fun extractListComments(resp:JSONObject): List<Comments> {
        val comentarios: MutableList<Comments> = mutableListOf()
        val array: JSONArray = resp.getJSONArray("comments")
        for (i in 1.. array.length()){
            val commentJson: JSONObject = array.getJSONObject(i)
            val commentReturn: Comments = Comments(commentJson.getInt("id"),commentJson.getString("description"),commentJson.getInt("rating"))
            comentarios[i] = commentReturn
        }
        return comentarios
    }

    fun extractListArtist(resp:JSONObject): List<Artist> {
        val artistas: MutableList<Artist> = mutableListOf()
        val array: JSONArray = resp.getJSONArray("favoritePerformers")
        for (i in 1.. array.length()){
            val artistJson: JSONObject = array.getJSONObject(i)
            val artistReturn: Artist = Artist(artistJson.getInt("artistId"),artistJson.getString("name"),artistJson.getString("date"),artistJson.getString("description"),artistJson.getString("image"))
            artistas[i] = artistReturn
        }
        return artistas
    }

    /*fun getCollectorsOffline(onComplete: (resp: List<Collector>) -> Unit){
        val list = mutableListOf<Collector>()
        list.add(0, Collector(0,"Alexandrey","",""))
        list.add(1, Collector(1,"Lara","",""))
        list.add(2,Collector(2,"Melissa","",""))
        list.add(3,Collector(3,"Santiago","",""))
        list.add(4,Collector(4,"Mateo","",""))
        onComplete(list)
    }*/
}