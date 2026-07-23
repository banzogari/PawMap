package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.pawmap.app.R
import com.pawmap.app.databinding.FragmentTripNameBinding

class TripNameFragment : Fragment() {

    private var _binding: FragmentTripNameBinding? = null
    private val binding get() = _binding!!
    private val vm: TripNameViewModel by viewModels()

    private var startDate = 0L
    private var endDate = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startDate = arguments?.getLong("startDate") ?: 0L
        endDate = arguments?.getLong("endDate") ?: 0L

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.etPet.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addPetChip(v.text?.toString().orEmpty())
                true
            } else false
        }

        binding.btnDone.setOnClickListener { createTrip() }
    }

    private fun addPetChip(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        val chip = Chip(requireContext()).apply {
            text = trimmed
            isCloseIconVisible = true
            setChipBackgroundColorResource(R.color.accent_container)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.accent))
            setOnCloseIconClickListener { binding.chipGroupPets.removeView(this) }
        }
        binding.chipGroupPets.addView(chip)
        binding.etPet.setText("")
    }

    private fun collectPets(): List<String> {
        val pets = mutableListOf<String>()
        for (i in 0 until binding.chipGroupPets.childCount) {
            (binding.chipGroupPets.getChildAt(i) as? Chip)?.let { pets.add(it.text.toString()) }
        }
        // Also include any text left in the field.
        binding.etPet.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }?.let { pets.add(it) }
        return pets
    }

    private fun createTrip() {
        val name = binding.etTripName.text?.toString().orEmpty()
        vm.createTrip(name, startDate, endDate, collectPets()) { tripId ->
            findNavController().navigate(
                R.id.action_tripName_to_tripDetail, bundleOf("tripId" to tripId)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
