package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pawmap.app.R
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.databinding.FragmentTripMainBinding

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
                binding.tvCreateTitle.text = "여행 이어보기 · ${trip.name}"
                binding.icCreate.setImageResource(R.drawable.ic_flight)
            } else {
                binding.tvCreateTitle.text = getString(R.string.create_trip)
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
                findNavController().navigate(R.id.action_tripMain_to_datePicker)
            }
        }

        binding.btnPastTrips.setOnClickListener {
            findNavController().navigate(R.id.action_tripMain_to_pastTrips)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}