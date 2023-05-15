package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentAddTrackBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Track
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

                if (name.isEmpty() || duration.isEmpty()) {
                    Toast.makeText(requireContext(), "Please make sure all fields have been filled correctly", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (name.length > 100) {
                    Toast.makeText(requireContext(), "Please enter a track title with less than 100 characters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val durationFormat = Regex("""^([0-5][0-9]):([0-5][0-9])$""")
                if (!durationFormat.matches(duration)) {
                    Toast.makeText(requireContext(), "Please enter the duration in the format MM:SS", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val track = Track(
                    trackId = 0,
                    name = name,
                    duration = duration
                )

                viewModel.addTrackToAlbum(albumId.toInt(), track)
                Toast.makeText(requireContext(), "Track added successfully", Toast.LENGTH_SHORT).show()

                findNavController().popBackStack(R.id.albumDetailFragment, false)

            }
        }

        return binding.root
    }

}