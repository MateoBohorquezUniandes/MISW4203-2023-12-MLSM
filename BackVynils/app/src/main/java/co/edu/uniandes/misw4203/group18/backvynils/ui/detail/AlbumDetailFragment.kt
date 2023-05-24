package co.edu.uniandes.misw4203.group18.backvynils.ui.detail

import androidx.navigation.fragment.findNavController
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentAlbumDetailBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.AlbumWithTracks
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.AlbumViewModel

/**
 * A simple [Fragment] subclass for rendering
 * and managing the detail of an album
 */
class AlbumDetailFragment : Fragment() {

    companion object {
        const val ALBUM_ID = "albumId"
    }

    // ViewModel Pattern class properties
    private lateinit var viewModel: AlbumViewModel

    private lateinit var navController: NavController

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumId: String
    private lateinit var albumWithTracksDetail: AlbumWithTracks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }
        val activity = requireNotNull(this.activity) {
            "Cannot access viewModel before onActivityCreated()"
        }

        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        binding.addTrackButton.setOnClickListener {
            val navController = findNavController()
            val action =
                AlbumDetailFragmentDirections.actionAlbumDetailFragmentToAddTrackFragment(albumId)
            navController.navigate(action)
        }
        binding.lifecycleOwner = activity

        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )

        setupAlbumViewModel(activity)

        return binding.root
    }

    private fun setupAlbumViewModel(activity: FragmentActivity) {
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(activity.application)
        )[AlbumViewModel::class.java]

        viewModel.getAlbumDetailDataFromNetwork(albumId = albumId.toInt())

        viewModel.albumWithTracks.observe(viewLifecycleOwner) { albumDetail: AlbumWithTracks? ->
            albumDetail?.let {
                Log.d(
                    "AlbumDetail",
                    "New Album Detail: ${it.album.name} (${it.tracks.size} tracks)"
                )
                for (track in it.tracks) {
                    Log.d("AlbumDetail", "Track (${track.trackId}, FK ${track.fkAlbumId})")
                }
                albumWithTracksDetail = it
                binding.albumWithTracks = albumWithTracksDetail
                viewModel.storeAlbumWithTracksFromNetwork(it)
            }
        }

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner
        ) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun onNetworkError(message: String = "Connectivity Error") {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
    }
}