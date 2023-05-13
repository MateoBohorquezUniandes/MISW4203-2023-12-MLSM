package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentAddTrackBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.AlbumViewModel

class AddTrackFragment : Fragment() {
    private lateinit var viewModel: AlbumViewModel
    private lateinit var binding: FragmentAddTrackBinding
    companion object {
        const val ALBUM_ID = "albumId"
    }

    private lateinit var albumId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getString(ALBUM_ID).orEmpty() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddTrackBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))[AlbumViewModel::class.java]

        binding.apply {
            createButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val duration = durationEditText.text.toString()

                val track = Album.Track(
                    id = 0,
                    name = name,
                    duration = duration
                )

                viewModel.addTrackToAlbum(albumId.toInt(), track)
                Toast.makeText(requireContext(), "Track created successfully", Toast.LENGTH_SHORT).show()

            }
        }

        return binding.root
    }

}