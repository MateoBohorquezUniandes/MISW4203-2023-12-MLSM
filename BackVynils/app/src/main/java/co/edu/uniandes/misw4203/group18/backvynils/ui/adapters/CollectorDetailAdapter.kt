package co.edu.uniandes.misw4203.group18.backvynils.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.ArtistItemBinding
import co.edu.uniandes.misw4203.group18.backvynils.databinding.CollectorItemBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Artist
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.ui.CollectorListFragmentDirections

class CollectorDetailAdapter: RecyclerView.Adapter<CollectorDetailAdapter.CollectorViewHolder>(){

    class CollectorViewHolder(val viewDataBinding: ArtistItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withArtistDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), CollectorViewHolder.LAYOUT, parent, false
        )
        return CollectorViewHolder(withArtistDataBinding)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also { it.artist = artists[position]}
        }
}