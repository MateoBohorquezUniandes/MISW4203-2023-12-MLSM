package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentCollectorListBinding
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.CollectorsAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.CollectorViewModel

class CollectorListFragment : Fragment() {
    private var _binding: FragmentCollectorListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelAdapter = CollectorsAdapter()
        _binding = FragmentCollectorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity){
            "Cannot access viewModel before onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_collectors_list_fragment)
        viewModel = ViewModelProvider(
            this, CollectorViewModel.Factory(activity.application)
        ).get(CollectorViewModel::class.java)

        viewModel.collectors.observe(viewLifecycleOwner){
            Log.i("Collectors", viewModel.collectors.toString())
            if(it.isNotEmpty()) {
                it.apply {
                    viewModelAdapter!!.collectors = this
                }
            }
        }
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner
        ) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }


    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Connectivity Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
