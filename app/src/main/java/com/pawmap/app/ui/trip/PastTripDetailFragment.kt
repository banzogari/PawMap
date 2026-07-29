package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.pawmap.app.R
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.databinding.FragmentPastTripDetailBinding
import com.pawmap.app.databinding.ItemJournalSectionBinding
import com.pawmap.app.util.DateUtils

class PastTripDetailFragment : Fragment() {

    private var _binding: FragmentPastTripDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: PastTripDetailViewModel by viewModels()

    // 현재 여행의 목적지 키 (bindHeader에서 설정, renderSections에서 사용)
    private var destKey: String = "OSAKA"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastTripDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tripId = arguments?.getLong("tripId") ?: -1L
        vm.load(tripId)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        vm.trip.observe(viewLifecycleOwner) { trip -> trip?.let { bindHeader(it) } }
        vm.sections.observe(viewLifecycleOwner) { renderSections(it) }
    }

    private fun bindHeader(trip: TripEntity) {
        binding.tvTitle.text = trip.name
        val pets = trip.petNames.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val petText = if (pets.isEmpty()) "" else " · " + pets.joinToString(", ") + "와"
        binding.tvSub.text = DateUtils.formatRange(trip.startDate, trip.endDate) + petText

        destKey = destKeyFor(trip.name)
    }

    private fun destKeyFor(tripName: String): String = when {
        tripName.contains("오사카") -> "OSAKA"
        tripName.contains("강릉") || tripName.contains("속초") -> "GANGNEUNG"
        tripName.contains("경주") -> "GYEONGJU"
        else -> "OSAKA"
    }

    /** 목적지별 Day1/Day2/Day3 시연용 일러스트 (dayIndex 순서 그대로 사용). */
    private val dayImages: Map<String, List<Int>> = mapOf(
        "OSAKA" to listOf(
            R.drawable.img_journal_osaka_day1,
            R.drawable.img_journal_osaka_day2,
            R.drawable.img_journal_osaka_day3
        ),
        "GANGNEUNG" to listOf(
            R.drawable.img_journal_gangneung_day1,
            R.drawable.img_journal_gangneung_day2,
            R.drawable.img_journal_gangneung_day3
        ),
        "GYEONGJU" to listOf(
            R.drawable.img_journal_gyeongju_day1,
            R.drawable.img_journal_gyeongju_day2,
            R.drawable.img_journal_gyeongju_day3
        )
    )

    /** Day N 자리에는 그 목적지의 dayN 이미지를 그대로 표시 (4일 이상이면 순환). */
    private fun imageForDay(key: String, dayIndex: Int): Int {
        val images = dayImages[key] ?: dayImages.getValue("OSAKA")
        return images[dayIndex % images.size]
    }

    private fun renderSections(sections: List<DaySection>) {
        binding.container.removeAllViews()
        for (s in sections) {
            val ib = ItemJournalSectionBinding.inflate(layoutInflater, binding.container, false)
            ib.tvDayLabel.text = s.dateLabel

            if (!s.photoUri.isNullOrBlank()) {
                // 실제로 사용자가 등록한 사진이 있으면 그걸 최우선으로 표시
                ib.photoPlaceholder.visibility = View.GONE
                ib.photoImage.visibility = View.VISIBLE
                ib.photoImage.load(s.photoUri)
            } else {
                // 시연용: Day 번호에 맞는 목적지 일러스트 표시
                ib.photoPlaceholder.visibility = View.GONE
                ib.photoImage.visibility = View.VISIBLE
                ib.photoImage.setImageResource(imageForDay(destKey, s.dayIndex))
            }

            if (!s.memo.isNullOrBlank()) {
                ib.tvMemo.text = s.memo
                ib.tvMemo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
            } else {
                ib.tvMemo.text = getString(R.string.no_memo)
                ib.tvMemo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_tertiary))
            }
            binding.container.addView(ib.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}