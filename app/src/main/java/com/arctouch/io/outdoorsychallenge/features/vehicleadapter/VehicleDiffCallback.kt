package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import androidx.recyclerview.widget.DiffUtil
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

object VehicleDiffCallback : DiffUtil.ItemCallback<Vehicle>() {
    override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle) = oldItem == newItem
}
