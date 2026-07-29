package com.pawmap.app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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

    private val adapter = SearchResultAdapter(
        onClick = { placeId ->
            findNavController().navigate(
                R.id.action_search_to_detail, bundleOf("placeId" to placeId)
            )
        },
        onFavorite = { placeId -> vm.toggleFavorite(placeId) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
