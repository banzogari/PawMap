package com.pawmap.app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawmap.app.R
import com.pawmap.app.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val vm: SearchResultViewModel by viewModels()

    // >0 when opened as a picker to add places to a specific saved-place list.
    private var addToListId: Long = -1L

    private val adapter = SearchResultAdapter(
        onClick = { placeId -> onPlaceClicked(placeId) },
        onFavorite = { placeId -> vm.toggleFavorite(placeId) }
    )

    private fun onPlaceClicked(placeId: Long) {
        if (addToListId > 0) {
            vm.addToList(addToListId, placeId) {
                Toast.makeText(requireContext(), "목록에 추가했어요", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        } else {
            findNavController().navigate(
                R.id.action_search_to_detail, bundleOf("placeId" to placeId)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addToListId = arguments?.getLong("addToListId") ?: -1L
        if (addToListId > 0) binding.etSearch.hint = "목록에 추가할 장소 검색"

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        vm.rows.observe(viewLifecycleOwner) { rows ->
            adapter.submitList(rows)
            binding.tvEmpty.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
        }

        // Initial query passed from the map screen.
        if (savedInstanceState == null) {
            val initial = arguments?.getString("query").orEmpty()
            binding.etSearch.setText(initial)
            vm.search(initial)
        }

        // Filter live as the user types (works regardless of the keyboard's
        // search/enter button, which the Korean IME often swallows for composition).
        binding.etSearch.doAfterTextChanged { text ->
            vm.search(text?.toString().orEmpty())
        }

        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.clearFocus()
                true
            } else false
        }

        binding.btnClear.setOnClickListener {
            binding.etSearch.setText("")
            vm.search("")
        }
    }

    override fun onResume() {
        super.onResume()
        // Reflect favorite changes made elsewhere.
        vm.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
