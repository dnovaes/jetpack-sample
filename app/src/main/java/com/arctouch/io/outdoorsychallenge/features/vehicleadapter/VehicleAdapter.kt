package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.io.outdoorsychallenge.databinding.ItemRvBinding
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.extensions.get
import org.koin.core.parameter.parametersOf

class VehicleAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<Vehicle, VehicleAdapter.ViewHolder>(VehicleDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            lifecycleOwner
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onViewRecycled(holder: ViewHolder) = holder.recycle()

    class ViewHolder(private val binding: ItemRvBinding, lifecycleOwner: LifecycleOwner) :
        RecyclerView.ViewHolder(binding.root) {

        private var viewModel: VehicleViewModel? = null

        init {
            binding.lifecycleOwner = lifecycleOwner
        }

        fun bind(item: Vehicle) {
            viewModel = get<VehicleViewModel> { parametersOf(item) }
                .apply { binding.viewModel = this }

            binding.executePendingBindings()
        }

        fun recycle() {
            viewModel = null
        }
    }
}

object VehicleDiffCallback : DiffUtil.ItemCallback<Vehicle>() {
    override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle) = oldItem == newItem
}
