package com.arctouch.io.outdoorsychallenge.features.searchrv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter
import com.arctouch.io.outdoorsychallenge.databinding.ItemRvBinding
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.features.vehicleadapter.VehicleDiffCallback
import com.arctouch.io.outdoorsychallenge.features.vehicleadapter.VehicleViewHolder

class SearchRvVehicleAdapter(private val lifecycleOwner: LifecycleOwner) :
    PagedListAdapter<Vehicle, VehicleViewHolder>(VehicleDiffCallback) {
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
