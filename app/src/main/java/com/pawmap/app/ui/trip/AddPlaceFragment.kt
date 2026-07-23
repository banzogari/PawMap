package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawmap.app.databinding.FragmentAddPlaceBinding

class AddPlaceFragment : Fragment() {

    private var _binding: FragmentAddPlaceBinding? = null
    private val binding get() = _binding!!
    private val vm: AddPlaceViewModel by viewModels()

    private var tripId = -1L
    private var dayIndex = 0

    private val adapter = PickPlaceAdapter(onPick = { placeId -> add(placeId) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tripId = arguments?.getLong("tripId") ?: -1L
        dayIndex = arguments?.getInt("dayIndex") ?: 0

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.etSearch.doAfterTextChanged { vm.filter(it?.toString().orEmpty()) }

        vm.places.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun add(placeId: Long) {
        vm.addToTrip(tripId, dayIndex, placeId) {
            Toast.makeText(requireContext(), "일정에 추가했어요", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
