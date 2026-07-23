package com.pawmap.app.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pawmap.app.data.dao.ListWithCount
import com.pawmap.app.data.model.ListIconType
import com.pawmap.app.databinding.ItemPlaceListBinding

class PlaceListAdapter(
    private val onClick: (ListWithCount) -> Unit,
    private val onMore: (ListWithCount, android.view.View) -> Unit
) : ListAdapter<ListWithCount, PlaceListAdapter.VH>(DIFF) {

    inner class VH(val b: ItemPlaceListBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemPlaceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val b = holder.b
        val icon = ListIconType.fromName(item.iconType)
        b.icon.setImageResource(icon.iconRes)
        b.icon.setColorFilter(ContextCompat.getColor(b.root.context, icon.tintRes))
        b.tvName.text = item.name
        b.tvCount.text = "${item.placeCount}개 장소"
        b.root.setOnClickListener { onClick(item) }
        b.btnMore.setOnClickListener { onMore(item, it) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ListWithCount>() {
            override fun areItemsTheSame(a: ListWithCount, b: ListWithCount) = a.id == b.id
            override fun areContentsTheSame(a: ListWithCount, b: ListWithCount) = a == b
        }
    }
}
