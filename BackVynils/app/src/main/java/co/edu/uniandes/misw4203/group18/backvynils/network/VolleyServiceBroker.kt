package co.edu.uniandes.misw4203.group18.backvynils.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyServiceBroker constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://vynils-back-heroku.herokuapp.com/"
        private var instance: VolleyServiceBroker? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: VolleyServiceBroker(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getRequest(
        path: String, respListener: Response.Listener<String>, errListener: Response.ErrorListener
    ) {
        requestQueue.add(
            StringRequest(
                Request.Method.GET,
                BASE_URL + path,
                respListener,
                errListener
            )
        )
    }

    fun postRequest(
        path: String,
        body: JSONObject,
        respListener: Response.Listener<JSONObject>,
        errListener: Response.ErrorListener
    ) {
        requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + path,
                body,
                respListener,
                errListener
            )
        )
    }
}