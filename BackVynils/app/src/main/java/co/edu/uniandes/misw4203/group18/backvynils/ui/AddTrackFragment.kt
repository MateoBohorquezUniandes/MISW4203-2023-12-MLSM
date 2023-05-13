package co.edu.uniandes.misw4203.group18.backvynils.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.uniandes.misw4203.group18.backvynils.R

class AddTrackFragment : Fragment() {
    companion object {
        const val ALBUM_ID = "albumId"
    }

    private lateinit var albumId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { albumId = it.getString(ALBUM_ID).orEmpty() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_track, container, false)
    }

}