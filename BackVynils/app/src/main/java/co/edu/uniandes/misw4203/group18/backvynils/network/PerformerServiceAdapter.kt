package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import co.edu.uniandes.misw4203.group18.backvynils.models.Band
import co.edu.uniandes.misw4203.group18.backvynils.models.Musician
import com.android.volley.VolleyError
import org.json.JSONArray

class PerformerServiceAdapter constructor() {
    companion object {
        private var instance: PerformerServiceAdapter? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PerformerServiceAdapter().also {
                    instance = it
                }
            }
    }

    private val bandPath: String = "bands"
    private val musicianPath: String = "bands"

    fun getBands(
        context: Context,
        onComplete: (resp: List<Band>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            bandPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Band>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Band(
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            creationDate = item.getString("creationDate")
                        )
                    )
                }

                onComplete(list)
            },
            { onError(it) }
        )
    }

    fun getMusicians(
        context: Context,
        onComplete: (resp: List<Musician>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        VolleyServiceBroker.getInstance(context).getRequest(
            musicianPath,
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Musician(
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            birthDate = item.getString("birthDate")
                        )
                    )
                }

                onComplete(list)
            },
            { onError(it) }
        )
    }
}