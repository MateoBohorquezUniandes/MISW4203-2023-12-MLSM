package co.edu.uniandes.misw4203.group18.backvynils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentCollectorDetailBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.CollectorsAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.CollectorViewModel

class CollectorDetailFragment : Fragment() {

    companion object{
        const val COLLECTOR_ID = "collectorId"
    }
    private lateinit var collectorId: String
    private lateinit var collector: Collector

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null

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
        viewModelAdapter = CollectorsAdapter()
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_collector_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity =  requireNotNull(this.activity){
            "Cannot access viewModel before onActivityCreated()"
        }
        viewModel = ViewModelProvider(this,CollectorViewModel.Factory(activity.application)).get(CollectorViewModel::class.java)
        viewModel.collectors.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                it.apply {
                    viewModelAdapter!!.collectors = this
                    collector = viewModelAdapter!!.extractSingleCollector(collectorId)!!
                    binding.collector = collector
                }
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner){
            isNetworkError -> if (isNetworkError) onNetworkError()
        }
    }
    private fun onNetworkError(){
        if(!viewModel.isNetworkErrorShown.value!!){
            Toast.makeText(activity, "Connectivity Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}