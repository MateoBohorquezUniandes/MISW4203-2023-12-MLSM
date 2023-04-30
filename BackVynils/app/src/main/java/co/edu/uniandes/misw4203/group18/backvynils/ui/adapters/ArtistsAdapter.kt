package co.edu.uniandes.misw4203.group18.backvynils.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.ArtistItemBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.ui.ArtistListFragmentDirections


class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }

    var artists: List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withArtistDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), ArtistViewHolder.LAYOUT, parent, false
        )
        return ArtistViewHolder(withArtistDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also { it.artist = artists[position] }
        holder.viewDataBinding.root.setOnClickListener {
            val destination =
                ArtistListFragmentDirections.actionArtistListFragmentToArtistDetailFragment(artists[position].artistId)
            holder.viewDataBinding.root.findNavController().navigate(destination)
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }


}