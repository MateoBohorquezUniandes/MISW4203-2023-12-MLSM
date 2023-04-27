package co.edu.uniandes.misw4203.group18.backvynils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass for rendering
 * and managing the detail of an album
 */
class ArtistDetailFragment : Fragment() {

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

        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

}