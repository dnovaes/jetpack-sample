package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.io.outdoorsychallenge.databinding.ItemRvBinding
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.extensions.get
import org.koin.core.parameter.parametersOf

class VehicleViewHolder(private val binding: ItemRvBinding, lifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root) {

    private var viewModel: VehicleViewModel? = null

    init {
        binding.lifecycleOwner = lifecycleOwner
    }

    fun bind(item: Vehicle) {
        viewModel = get<VehicleViewModel> { parametersOf(item) }.apply { binding.viewModel = this }
        binding.executePendingBindings()
    }

    fun recycle() {
        viewModel = null
    }
}
