package co.edu.uniandes.misw4203.group18.backvynils.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.group18.backvynils.R
import co.edu.uniandes.misw4203.group18.backvynils.databinding.CollectorItemBinding
import co.edu.uniandes.misw4203.group18.backvynils.models.Collector
import co.edu.uniandes.misw4203.group18.backvynils.ui.CollectorListFragmentDirections
class CollectorsAdapter : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {

    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root){
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }

    var collectors: List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var collector: Collector = Collector(0,"","","",0,0)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withCollectorDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), CollectorViewHolder.LAYOUT, parent, false
        )
        return CollectorViewHolder(withCollectorDataBinding)
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also { it.collector = collectors[position]}
        holder.viewDataBinding.root.setOnClickListener {
            val destination =
                CollectorListFragmentDirections.actionCollectorListFragmentToCollectorDetailFragment(collectors[position].collectorId)
            holder.viewDataBinding.root.findNavController().navigate(destination)
        }
    }
    fun extractSingleCollector(id: String): Collector?{
        val collect: Collector? = collectors.find {
            col -> id.toInt().equals(col.collectorId)
        }
        return collect
    }

}