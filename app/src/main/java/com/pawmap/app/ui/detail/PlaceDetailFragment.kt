package com.pawmap.app.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.pawmap.app.R
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.databinding.FragmentPlaceDetailBinding
import com.pawmap.app.ui.common.bindOpenStatus
import com.pawmap.app.ui.common.bindSizeBadge
import com.pawmap.app.ui.common.visibleIf

class PlaceDetailFragment : Fragment() {

    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: PlaceDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val placeId = arguments?.getLong("placeId") ?: -1L
        vm.load(placeId)

        vm.place.observe(viewLifecycleOwner) { place -> place?.let { bind(it) } }
        vm.saved.observe(viewLifecycleOwner) { updateSaved(it) }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnSave.setOnClickListener { vm.toggleSaved() }
        binding.btnSaveTop.setOnClickListener { vm.toggleSaved() }
        binding.btnAddTrip.setOnClickListener { addToTrip() }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val overview = tab.position == 0
                binding.sectionOverview.visibleIf(overview)
                binding.sectionInfo.visibleIf(!overview)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun bind(p: PlaceEntity) {
        binding.tvName.text = p.name
        binding.tvSub.text = "${p.category} · ${p.region}"

        bindPhotos(p)

        // Overview
        binding.tvAddress.text = p.address
        binding.tvHours.bindOpenStatus(p.openNow, p.hoursText)
        binding.rowHours.visibleIf(!p.hoursText.isNullOrBlank())
        binding.tvPhone.text = p.phone ?: ""
        binding.rowPhone.visibleIf(!p.phone.isNullOrBlank())

        // Info: species chips
        binding.speciesContainer.removeAllViews()
        val types = p.animalTypes.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        binding.blockSpecies.visibleIf(types.isNotEmpty())
        types.forEach { binding.speciesContainer.addView(speciesChip(it)) }

        // Info: size badges
        binding.badgeSmall.bindSizeBadge("소형견", p.sizeSmall)
        binding.badgeMedium.bindSizeBadge("중형견", p.sizeMedium)
        binding.badgeLarge.bindSizeBadge("대형견", p.sizeLarge)

        // Info: text rows (hide when empty)
        binding.valIndoor.text = p.indoorText ?: ""
        binding.rowIndoor.visibleIf(!p.indoorText.isNullOrBlank())
        binding.valFee.text = p.extraFeeText ?: ""
        binding.rowFee.visibleIf(!p.extraFeeText.isNullOrBlank())
        binding.valRestrict.text = p.restrictionsText ?: ""
        binding.rowRestrict.visibleIf(!p.restrictionsText.isNullOrBlank())
        binding.valFacility.text = p.facilitiesText ?: ""
        binding.rowFacility.visibleIf(!p.facilitiesText.isNullOrBlank())
    }

    /** place_image에서 온 URL들로 헤더 페이저 + 썸네일 스트립을 채운다. 없으면 placeholder 유지. */
    private fun bindPhotos(p: PlaceEntity) {
        val urls = p.imageUrls?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }.orEmpty()

        if (urls.isEmpty()) {
            binding.photoPager.visibility = View.GONE
            binding.tvPhotoCount.visibility = View.GONE
            binding.imgPlaceholder.visibility = View.VISIBLE
            return
        }

        binding.imgPlaceholder.visibility = View.GONE
        binding.photoPager.visibility = View.VISIBLE
        binding.tvPhotoCount.visibility = View.VISIBLE
        binding.photoPager.adapter = PhotoPagerAdapter(urls)
        binding.tvPhotoCount.text = "1/${urls.size}"
        binding.photoPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvPhotoCount.text = "${position + 1}/${urls.size}"
            }
        })

        // 하단 썸네일 4칸: 있는 만큼 채우고 나머지는 placeholder 배경 유지
        val thumbs = listOf(
            binding.photoThumb1, binding.photoThumb2, binding.photoThumb3, binding.photoThumb4
        )
        thumbs.forEachIndexed { i, iv ->
            val url = urls.getOrNull(i)
            if (url != null) iv.load(url) {
                crossfade(true)
                listener(
                    onError = { _, result ->
                        Log.e("PawMapImg", "FAIL $url", result.throwable)
                    },
                    onSuccess = { _, _ ->
                        Log.d("PawMapImg", "OK $url")
                    }
                )
            }
        }
    }

    private class PhotoPagerAdapter(
        private val urls: List<String>
    ) : RecyclerView.Adapter<PhotoPagerAdapter.VH>() {

        class VH(val iv: ImageView) : RecyclerView.ViewHolder(iv)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val iv = ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            return VH(iv)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.iv.load(urls[position]) {
                crossfade(true)
                listener(
                    onError = { _, result ->
                        Log.e("PawMapImg", "FAIL(pager) ${urls[position]}", result.throwable)
                    }
                )
            }
        }

        override fun getItemCount() = urls.size
    }

    private fun speciesChip(text: String): TextView {
        val tv = TextView(requireContext())
        tv.text = text
        tv.setBackgroundResource(R.drawable.bg_badge_neutral)
        tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
        tv.textSize = 14f
        val padH = dp(14); val padV = dp(8)
        tv.setPadding(padH, padV, padH, padV)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.marginEnd = dp(8)
        tv.layoutParams = lp
        tv.gravity = Gravity.CENTER
        return tv
    }

    private fun updateSaved(saved: Boolean) {
        val icon = if (saved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
        binding.icSave.setImageResource(icon)
        binding.btnSaveTop.setImageResource(icon)
        binding.icSave.setColorFilter(ContextCompat.getColor(requireContext(), R.color.accent))
        binding.tvSaveLabel.text = if (saved) getString(R.string.saved) else getString(R.string.save)
        binding.btnSaveTop.setColorFilter(
            ContextCompat.getColor(requireContext(), if (saved) R.color.accent else R.color.white)
        )
    }

    private fun addToTrip() {
        vm.addToOngoingTrip { result ->
            when (result) {
                is PlaceDetailViewModel.AddTripResult.Added ->
                    Toast.makeText(
                        requireContext(),
                        "'${result.tripName}' Day 1에 추가했어요",
                        Toast.LENGTH_SHORT
                    ).show()

                PlaceDetailViewModel.AddTripResult.NoTrip -> {
                    Toast.makeText(
                        requireContext(),
                        "진행 중인 여행이 없어요. 여행을 먼저 만들어주세요",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.tripMainFragment)
                }
            }
        }
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}