package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.pawmap.app.R
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.databinding.FragmentTripMainBinding
import com.pawmap.app.util.DateUtils

class TripMainFragment : Fragment() {

    private var _binding: FragmentTripMainBinding? = null
    private val binding get() = _binding!!
    private val vm: TripMainViewModel by viewModels()

    private var ongoing: TripEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.ongoingTrip.observe(viewLifecycleOwner) { trip ->
            ongoing = trip
            if (trip != null) {
                binding.tvCreateTitle.text = "여행 이어보기"
                binding.tvCreateSub.text = trip.name
                binding.icCreate.setImageResource(R.drawable.ic_flight)
            } else {
                binding.tvCreateTitle.text = getString(R.string.create_trip)
                binding.tvCreateSub.text = getString(R.string.create_trip_sub)
                binding.icCreate.setImageResource(R.drawable.ic_add)
            }
        }

        binding.cardCreate.setOnClickListener {
            val current = ongoing
            if (current != null) {
                findNavController().navigate(
                    R.id.action_tripMain_to_tripDetail, bundleOf("tripId" to current.id)
                )
            } else {
                showDatePicker()
            }
        }

        binding.btnPastTrips.setOnClickListener {
            findNavController().navigate(R.id.action_tripMain_to_pastTrips)
        }
    }

    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(getString(R.string.pick_dates))
            .build()
        picker.addOnPositiveButtonClickListener { range ->
            val start = DateUtils.utcToLocalStartOfDay(range.first)
            val end = DateUtils.utcToLocalStartOfDay(range.second)
            findNavController().navigate(
                R.id.action_tripMain_to_tripName,
                bundleOf("startDate" to start, "endDate" to end)
            )
        }
        picker.show(childFragmentManager, "date_range")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
