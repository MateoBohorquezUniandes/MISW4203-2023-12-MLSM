package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getCollectors(context: Context) = suspendCoroutine<List<Collector>> { cont ->
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
                            comments = extractIdComments(item),
                            favoritePerformers = extractIdArtist(item)
                        )
                    )
                }
                cont.resume(list)
            },{
                cont.resumeWithException(it)
            }
        )
    }
    /*fun getCollectors(
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
                            comments = extractIdComments(item),
                            favoritePerformers = extractIdArtist(item)
                        )
                    )
                }
                onComplete(list)
            },{
                onError(it)
            }
        )
    }*/
    suspend fun getSingleCollector(
        id: String,
        context: Context) = suspendCoroutine<Collector> { cont ->
        VolleyServiceBroker.getInstance(context).getRequest(
            "$collectorPath/$id",
            {response ->
                val resp = JSONObject(response)
                val coll = Collector(
                    resp.getInt("id"),
                    resp.getString("name"),
                    resp.getString("telephone"),
                    resp.getString("email"),
                    extractIdComments(resp),
                    extractIdArtist(resp)
                )
                cont.resume(coll)
            },{
                cont.resumeWithException(it)
            }
        )
    }
    fun extractIdComments(resp:JSONObject): Int {
        val array: JSONArray = resp.getJSONArray("comments")
        val commentJson: JSONObject = array.getJSONObject(0)
        return commentJson.getInt("id")
    }

    fun extractIdArtist(resp:JSONObject): Int {
        val array: JSONArray = resp.getJSONArray("favoritePerformers")
        val commentJson: JSONObject = array.getJSONObject(0)
        return commentJson.getInt("id")
    }

    fun extractComments(resp: JSONObject): Comments {
        val array: JSONArray = resp.getJSONArray("comments")
        val commentJson: JSONObject = array.getJSONObject(0)
        return Comments(
            commentJson.getInt("id"),
            commentJson.getString("description"),
            commentJson.getInt("rating")
        )
    }
    suspend fun getCommentCollector(
        id: String,
        context: Context) = suspendCoroutine<Comments> { cont ->
        VolleyServiceBroker.getInstance(context).getRequest(
            "$collectorPath/$id",
            {response ->
                val resp = JSONObject(response)
                val comment: Comments = extractComments(resp)
                cont.resume(comment)
            },{
                cont.resumeWithException(it)
            }
        )
    }
}