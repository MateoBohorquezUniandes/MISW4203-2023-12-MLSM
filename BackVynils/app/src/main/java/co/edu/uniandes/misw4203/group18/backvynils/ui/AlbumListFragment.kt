package co.edu.uniandes.misw4203.group18.backvynils.ui
import androidx.navigation.fragment.findNavController
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentAlbumListBinding
import co.edu.uniandes.misw4203.group18.backvynils.ui.adapters.AlbumsAdapter
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.AlbumViewModel

/**
 * A simple [Fragment] subclass for rendering and
 * managing the list of albums
 */
class AlbumListFragment : Fragment() {
    // Data Binding class properties
    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!

    // View related attributes
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    // ViewModel Pattern class properties
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelAdapter = AlbumsAdapter()
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        // Add click listener to createAlbumButton
        binding.createAlbumButton.setOnClickListener {
            val action = AlbumListFragmentDirections.actionAlbumListFragmentToAlbumCreateFragment()
            val navController = findNavController()
            navController.navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById<ProgressBar>(R.id.albumListProgressBar)

        recyclerView = binding.albumsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "Cannot access viewModel before onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.title_albums_list_fragment)
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(activity.application)
        ).get(AlbumViewModel::class.java)

        viewModel.albums.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                progressBar.visibility = GONE
                it.apply {
                    viewModelAdapter!!.albums = this
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "Cannot access viewModel before onActivityCreated()"
        }

        activity.actionBar?.title = getString(R.string.title_albums_list_fragment)
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(activity.application)
        ).get(AlbumViewModel::class.java)

        viewModel.albums.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                progressBar.visibility = GONE
                it.apply {
                    viewModelAdapter!!.albums = this
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