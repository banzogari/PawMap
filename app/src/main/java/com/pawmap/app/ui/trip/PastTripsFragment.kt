package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawmap.app.R
import com.pawmap.app.databinding.FragmentPastTripsBinding

class PastTripsFragment : Fragment() {

    private var _binding: FragmentPastTripsBinding? = null
    private val binding get() = _binding!!
    private val vm: PastTripsViewModel by viewModels()

    private val adapter = PastTripsAdapter(onClick = { tripId ->
        findNavController().navigate(
            R.id.action_pastTrips_to_pastTripDetail, bundleOf("tripId" to tripId)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        vm.pastTrips.observe(viewLifecycleOwner) { trips ->
            adapter.submitList(trips)
            binding.tvEmpty.visibility = if (trips.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
