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
    }

    private fun renderSections(sections: List<DaySection>) {
        binding.container.removeAllViews()
        for (s in sections) {
            val ib = ItemJournalSectionBinding.inflate(layoutInflater, binding.container, false)
            ib.tvDayLabel.text = s.dateLabel

            if (!s.photoUri.isNullOrBlank()) {
                ib.photoPlaceholder.visibility = View.GONE
                ib.photoImage.visibility = View.VISIBLE
                ib.photoImage.load(s.photoUri)
            } else {
                ib.photoPlaceholder.visibility = View.VISIBLE
                ib.photoImage.visibility = View.GONE
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
