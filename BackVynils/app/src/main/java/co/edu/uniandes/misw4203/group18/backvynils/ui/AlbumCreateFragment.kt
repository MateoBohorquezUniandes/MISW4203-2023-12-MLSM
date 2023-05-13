package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.text.SimpleDateFormat
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.misw4203.group18.backvynils.R
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.group18.backvynils.databinding.AlbumCreateFragmentBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.AlbumViewModel
import java.text.ParseException


/**
 * A simple [Fragment] subclass.
 * Use the [AlbumCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumCreateFragment : Fragment() {
    private lateinit var viewModel: AlbumViewModel
    private lateinit var binding: AlbumCreateFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))[AlbumViewModel::class.java]

        with(binding) {
            createButton.setOnClickListener {
                val name = titleEditText.text.toString()
                val cover = urlEditText.text.toString()
                val releaseDate = releaseDateEditText.text.toString()
                val description = descriptionEditText.text.toString()
                val genre = genreSpinner.selectedItem.toString()
                val recordLabel = recordLabelSpinner.selectedItem.toString()


            if (name.isEmpty() || description.isEmpty() || releaseDate.isEmpty() || genre.isEmpty() || recordLabel.isEmpty()|| cover.isEmpty()) {
                Toast.makeText(requireContext(), "Please make sure all fields have been filled correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.length > 100) {
                Toast.makeText(requireContext(), "Please enter a name with less than 100 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cover.length > 200) {
                Toast.makeText(requireContext(), "Please enter a cover image URL with less than 200 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.length > 1000) {
                Toast.makeText(requireContext(), "Please enter a description with less than 1000 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            @SuppressLint("SimpleDateFormat")
            val desiredFormat = SimpleDateFormat("yyyy-MM-dd")
            try {
                desiredFormat.parse(releaseDate)
            } catch (e: ParseException) {
                Toast.makeText(requireContext(), "Please enter the release date in the format yyyy-mm-dd", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (genre == "Choose a genre") {
                Toast.makeText(requireContext(), "Please choose a genre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (recordLabel == "Choose a record label") {
                Toast.makeText(requireContext(), "Please choose a record label", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val album = Album(
                albumId = 0,
                name = name,
                cover = cover,
                releaseDate = releaseDate,
                description = description,
                genre = genre,
                recordLabel = recordLabel
            )

            viewModel.postAlbum(album)
            Toast.makeText(requireContext(), "Album created successfully", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack(R.id.albumListFragment, false)

        }}
    }


    companion object {
        @JvmStatic
        fun newInstance() = AlbumCreateFragment().apply {
            arguments = Bundle()
        }
    }
}
