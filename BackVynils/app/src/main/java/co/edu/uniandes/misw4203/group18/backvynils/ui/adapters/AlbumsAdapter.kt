package co.edu.uniandes.misw4203.group18.backvynils.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.AlbumItemBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Album
import co.edu.uniandes.misw4203.group18.backvynils.ui.AlbumListFragmentDirections

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

    var albums: List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withAlbumDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), AlbumViewHolder.LAYOUT, parent, false
        )
        return AlbumViewHolder(withAlbumDataBinding)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also { it.album = albums[position] }
        holder.viewDataBinding.root.setOnClickListener {
            val destination =
                AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailFragment(albums[position].albumId)
            holder.viewDataBinding.root.findNavController().navigate(destination)
        }
    }
}