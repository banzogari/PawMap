package com.pawmap.app.ui.saved

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
import com.pawmap.app.databinding.FragmentListDetailBinding

class ListDetailFragment : Fragment() {

    private var _binding: FragmentListDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: ListDetailViewModel by viewModels()

    private val adapter = ListPlaceAdapter(
        onClick = { placeId ->
            findNavController().navigate(
                R.id.action_listDetail_to_detail, bundleOf("placeId" to placeId)
            )
        },
        onRemove = { placeId -> vm.remove(placeId) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listId = arguments?.getLong("listId") ?: -1L
        vm.load(listId)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        // Open search in "add to this list" mode.
        binding.btnAddPlace.setOnClickListener {
            findNavController().navigate(
                R.id.action_listDetail_to_search, bundleOf("addToListId" to listId)
            )
        }

        vm.listName.observe(viewLifecycleOwner) { binding.tvTitle.text = it }
        vm.places.observe(viewLifecycleOwner) { places ->
            adapter.submitList(places)
            binding.tvCount.text = "${places.size}개 장소"
            binding.tvEmpty.visibility = if (places.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
