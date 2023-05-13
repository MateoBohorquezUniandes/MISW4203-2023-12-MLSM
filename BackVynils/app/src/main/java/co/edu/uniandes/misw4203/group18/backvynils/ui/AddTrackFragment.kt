package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.uniandes.misw4203.group18.backvynils.AlbumDetailFragment
import co.edu.uniandes.misw4203.group18.backvynils.R

class AddTrackFragment : Fragment() {

    companion object {
        const val ALBUM_ID = "albumId"
    }

    private lateinit var albumId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getInt(ALBUM_ID).toString() }

        return inflater.inflate(R.layout.fragment_add_track, container, false)
    }

}