package com.pawmap.app.ui.detail

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        val tint = if (saved) R.color.accent else R.color.text_primary
        binding.icSave.setColorFilter(ContextCompat.getColor(requireContext(), tint))
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
                        "‘${result.tripName}’ Day 1에 추가했어요",
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
