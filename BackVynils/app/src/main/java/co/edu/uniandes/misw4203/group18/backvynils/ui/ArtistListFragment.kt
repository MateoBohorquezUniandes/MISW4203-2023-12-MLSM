package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentArtistListBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.ArtistsAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.ArtistViewModel

class ArtistListFragment : Fragment(),
    ArtistsAdapter.ArtistItemListener {
    // Data Binding class properties
    private var _binding: FragmentArtistListBinding? = null
    private val binding get() = _binding!!

    // View related attributes
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    // ViewModel Pattern class properties
    private lateinit var viewModel: ArtistViewModel
    private var viewModelAdapter: ArtistsAdapter? = null

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        viewModelAdapter = ArtistsAdapter(this)

        _binding = FragmentArtistListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            requireActivity()
        ).get(ArtistViewModel::class.java)

        viewModel.musicians.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                progressBar.visibility = View.GONE
                it.apply {
                    viewModelAdapter!!.artists = this
                }
                viewModel.storeDataFromNetwork()
            }
        }

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner
        ) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById<ProgressBar>(R.id.artistListProgressBar)

        recyclerView = binding.artistsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter


        val activity = requireNotNull(this.activity) {
            "Cannot access viewModel before onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.title_artists_list_fragment)
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

    override fun onArtistItemCLick(artist: Artist) {
        navController.navigate(R.id.action_artistListFragment_to_artistDetailFragment)
        viewModel.selectedArtist.value = artist
    }
}
