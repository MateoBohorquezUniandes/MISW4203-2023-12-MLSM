package co.edu.uniandes.misw4203.group18.backvynils.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.viewmodels.ArtistViewModel

/**
 * A simple [Fragment] subclass for rendering
 * and managing the detail of an album
 */
class ArtistDetailFragment : Fragment() {

    // ViewModel Pattern class properties
    private lateinit var viewModel: ArtistViewModel

    private lateinit var navController: NavController

    companion object {
        const val ARTIST_ID = "artistId"
    }

    private lateinit var artistId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { artistId = it.getInt(ARTIST_ID).toString() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        arguments?.let { artistId = it.getInt(ARTIST_ID).toString() }

        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )

        viewModel = ViewModelProvider(
            requireActivity()
        ).get(ArtistViewModel::class.java)

        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }
}