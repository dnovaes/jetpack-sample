package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import com.arctouch.io.outdoorsychallenge.databinding.ItemRvBinding
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<Vehicle, VehicleViewHolder>(VehicleDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VehicleViewHolder(
            ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            lifecycleOwner
        )

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onViewRecycled(holder: VehicleViewHolder) = holder.recycle()
}
