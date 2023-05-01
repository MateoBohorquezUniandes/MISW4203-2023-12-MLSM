package co.edu.uniandes.misw4203.group18.backvynils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
class CollectorDetail : Fragment() {

    companion object{
        const val COLLECTOR_ID = "albumId"
    }
    private lateinit var collectorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arguments?.let { collectorId = it.getInt(COLLECTOR_ID).toString()}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        arguments?.let { collectorId = it.getInt(COLLECTOR_ID).toString() }
        return inflater.inflate(R.layout.fragment_collector_detail, container, false)
    }
}