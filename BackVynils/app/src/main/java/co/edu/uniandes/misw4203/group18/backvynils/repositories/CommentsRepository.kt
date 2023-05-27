package co.edu.uniandes.misw4203.group18.backvynils.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.edu.uniandes.misw4203.group18.backvynils.database.dao.CommentsDao
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments
import co.edu.uniandes.misw4203.group18.backvynils.network.CollectorServiceAdapter
import com.android.volley.VolleyError
class CommentsRepository (private val application: Application, private val commentsDao: CommentsDao) {
    suspend fun getCommentCollector(searchId: Int, collectorId: Int): Comments {
        var cached = commentsDao.getComments(searchId)
        return if(cached == null){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Comments(0,"",0)
            } else CollectorServiceAdapter.getInstance().getCommentCollector(collectorId.toString(),application)
        } else cached
    }
}