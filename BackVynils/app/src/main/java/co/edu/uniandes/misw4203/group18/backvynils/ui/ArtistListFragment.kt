package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentArtistListBinding
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.ArtistsAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.ArtistViewModel

class ArtistListFragment : Fragment() {
    // Data Binding class properties
    private var _binding: FragmentArtistListBinding? = null
    private val binding get() = _binding!!

    // View related attributes
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    // ViewModel Pattern class properties
    private lateinit var viewModel: ArtistViewModel
    private var viewModelAdapter: ArtistsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelAdapter = ArtistsAdapter()

        _binding = FragmentArtistListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById<ProgressBar>(R.id.artistListProgressBar)

        recyclerView = binding.artistsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "Cannot access viewModel before onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.title_artists_list_fragment)
        viewModel = ViewModelProvider(
            this,
            ArtistViewModel.Factory(activity.application)
        ).get(ArtistViewModel::class.java)

        viewModel.musicians.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                progressBar.visibility = View.GONE
                it.apply {
                    viewModelAdapter!!.artists = this
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

    override fun onResume() {
        super.onResume()
        viewModel.refreshDataFromNetwork()
    }
}
