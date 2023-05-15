package co.edu.uniandes.misw4203.group18.backvynils
import androidx.navigation.fragment.findNavController
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.uniandes.misw4203.group18.backvynils.databinding.FragmentAlbumDetailBinding

/**
 * A simple [Fragment] subclass for rendering
 * and managing the detail of an album
 */
class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    companion object {
        const val ALBUM_ID = "albumId"
    }

    private lateinit var albumId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.addTrackButton.setOnClickListener {
            val navController = findNavController()
            val action = AlbumDetailFragmentDirections.actionAlbumDetailFragmentToAddTrackFragment(albumId)
            navController.navigate(action)
        }

        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }
        return rootView
    }



}