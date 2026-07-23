package com.pawmap.app.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pawmap.app.R
import com.pawmap.app.data.dao.ListWithCount
import com.pawmap.app.databinding.FragmentSavedPlacesBinding

class SavedPlacesFragment : Fragment() {

    private var _binding: FragmentSavedPlacesBinding? = null
    private val binding get() = _binding!!
    private val vm: SavedViewModel by viewModels()

    private var allLists: List<ListWithCount> = emptyList()

    private val adapter = PlaceListAdapter(
        onClick = { list ->
            findNavController().navigate(
                R.id.action_saved_to_listDetail, bundleOf("listId" to list.id)
            )
        },
        onMore = { list, anchor -> showMore(list, anchor) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        vm.lists.observe(viewLifecycleOwner) {
            allLists = it
            applyFilter(binding.etSearch.text?.toString().orEmpty())
        }

        binding.etSearch.doAfterTextChanged { applyFilter(it?.toString().orEmpty()) }
        binding.btnNewList.setOnClickListener { showNewListDialog() }
    }

    private fun applyFilter(query: String) {
        val q = query.trim()
        val filtered = if (q.isEmpty()) allLists else allLists.filter { it.name.contains(q) }
        adapter.submitList(filtered)
    }

    private fun showNewListDialog() {
        val input = EditText(requireContext()).apply {
            hint = "목록 이름"
            setPadding(48, 32, 48, 32)
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("새 목록")
            .setView(input)
            .setPositiveButton("만들기") { _, _ ->
                vm.createList(input.text?.toString().orEmpty()) { ok ->
                    if (!ok) Toast.makeText(
                        requireContext(), "이름이 비어 있거나 이미 있는 목록이에요", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showMore(list: ListWithCount, anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menu.add("삭제")
        popup.setOnMenuItemClickListener {
            if (list.isDefault) {
                Toast.makeText(requireContext(), "기본 목록은 삭제할 수 없어요", Toast.LENGTH_SHORT).show()
            } else {
                vm.deleteList(list.id)
            }
            true
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
