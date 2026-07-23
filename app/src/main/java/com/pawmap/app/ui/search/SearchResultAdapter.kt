package com.pawmap.app.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pawmap.app.R
import com.pawmap.app.databinding.ItemSearchResultBinding
import com.pawmap.app.ui.common.bindBadge

class SearchResultAdapter(
    private val onClick: (Long) -> Unit,
    private val onFavorite: (Long) -> Unit
) : ListAdapter<PlaceRow, SearchResultAdapter.VH>(DIFF) {

    inner class VH(val b: ItemSearchResultBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val row = getItem(position)
        val p = row.place
        val b = holder.b
        b.tvName.text = p.name
        b.badgeSpecies.bindBadge(p.speciesBadge, p.speciesBadgeType)
        b.badgeSize.bindBadge(p.sizeBadge, p.sizeBadgeType)
        b.tvCategory.text = p.category

        b.tvStatus.text = if (p.openNow) "영업 중" else "영업 종료"
        b.tvStatus.setTextColor(
            ContextCompat.getColor(
                b.root.context,
                if (p.openNow) R.color.positive else R.color.negative
            )
        )
        b.tvDesc.text = p.oneLiner?.let { " · $it" } ?: ""

        if (row.isFavorite) {
            b.btnStar.setImageResource(R.drawable.ic_star_filled)
            b.btnStar.setColorFilter(ContextCompat.getColor(b.root.context, R.color.warning))
        } else {
            b.btnStar.setImageResource(R.drawable.ic_star)
            b.btnStar.setColorFilter(ContextCompat.getColor(b.root.context, R.color.text_secondary))
        }

        b.root.setOnClickListener { onClick(p.id) }
        b.btnStar.setOnClickListener { onFavorite(p.id) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<PlaceRow>() {
            override fun areItemsTheSame(a: PlaceRow, b: PlaceRow) = a.place.id == b.place.id
            override fun areContentsTheSame(a: PlaceRow, b: PlaceRow) = a == b
        }
    }
}
