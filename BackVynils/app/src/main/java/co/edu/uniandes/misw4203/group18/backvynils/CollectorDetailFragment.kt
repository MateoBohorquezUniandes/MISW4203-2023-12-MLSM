package co.edu.uniandes.misw4203.group18.backvynils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentCollectorDetailBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.models.Comments
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.CollectorDetailAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.CollectorDetailViewModel

class CollectorDetail : Fragment() {

    companion object{
        const val COLLECTOR_ID = "collectorId"
    }
    private lateinit var collectorId: String
    private lateinit var collector: Collector
    private lateinit var comments: Comments

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: CollectorDetailViewModel
    private var viewModelAdapter: CollectorDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arguments?.let { collectorId = it.getInt(COLLECTOR_ID).toString()}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        arguments?.let { collectorId = it.getInt(COLLECTOR_ID).toString() }
        viewModelAdapter = CollectorDetailAdapter()
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_collector_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorDetailRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity =  requireNotNull(this.activity){
            "Cannot access viewModel before onActivityCreated()"
        }
        viewModel = ViewModelProvider(this,CollectorDetailViewModel.Factory(activity.application,collectorId.toInt())).get(CollectorDetailViewModel::class.java)
        viewModel.collector.observe(viewLifecycleOwner){
            if (it != Collector(0,"","","",0,0)){
                it.apply {
                    collector = this
                    //viewModelAdapter!!.collector = this
                    binding.collector = collector
                }
            }
        }
        viewModel.artist.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                it.apply {
                    viewModelAdapter!!.artists = this
                }
            }
        }
        viewModel.comment.observe(viewLifecycleOwner){
            if (it != Comments(0,"",0)){
                it.apply {
                    comments = this
                    binding.comments = this
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