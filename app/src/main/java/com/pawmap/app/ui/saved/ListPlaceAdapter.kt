package com.pawmap.app.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.databinding.ItemListPlaceBinding
import com.pawmap.app.ui.common.category

class ListPlaceAdapter(
    private val onClick: (Long) -> Unit,
    private val onRemove: (Long) -> Unit
) : ListAdapter<PlaceEntity, ListPlaceAdapter.VH>(DIFF) {

    inner class VH(val b: ItemListPlaceBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemListPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = getItem(position)
        val b = holder.b
        b.tvName.text = p.name
        b.tvCategory.text = p.category
        b.thumbIcon.setImageResource(p.category().iconRes)
        b.thumbIcon.setColorFilter(ContextCompat.getColor(b.root.context, R.color.accent))
        b.root.setOnClickListener { onClick(p.id) }
        b.btnRemove.setOnClickListener { onRemove(p.id) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<PlaceEntity>() {
            override fun areItemsTheSame(a: PlaceEntity, b: PlaceEntity) = a.id == b.id
            override fun areContentsTheSame(a: PlaceEntity, b: PlaceEntity) = a == b
        }
    }
}
