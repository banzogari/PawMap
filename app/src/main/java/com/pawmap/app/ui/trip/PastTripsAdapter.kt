package com.pawmap.app.ui.trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.databinding.ItemPastTripBinding
import com.pawmap.app.util.DateUtils

class PastTripsAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<TripEntity, PastTripsAdapter.VH>(DIFF) {

    inner class VH(val b: ItemPastTripBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemPastTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val trip = getItem(position)
        val b = holder.b
        b.tvName.text = trip.name
        val pets = trip.petNames.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val petText = if (pets.isEmpty()) "" else " · " + pets.joinToString(", ") + "와"
        b.tvSub.text = DateUtils.formatRange(trip.startDate, trip.endDate) + petText
        b.root.setOnClickListener { onClick(trip.id) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<TripEntity>() {
            override fun areItemsTheSame(a: TripEntity, b: TripEntity) = a.id == b.id
            override fun areContentsTheSame(a: TripEntity, b: TripEntity) = a == b
        }
    }
}
