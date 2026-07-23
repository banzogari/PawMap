package com.pawmap.app.ui.trip

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import coil.load
import com.pawmap.app.R
import com.pawmap.app.databinding.DialogJournalEditBinding

class JournalEditDialogFragment : DialogFragment() {

    private var _binding: DialogJournalEditBinding? = null
    private val binding get() = _binding!!
    private val vm: JournalEditViewModel by viewModels()

    private var tripId = -1L
    private var dayIndex = 0
    private var photoUri: String? = null

    private val pickImage = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                requireContext().contentResolver.takePersistableUriPermission(
                    uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Photo-picker URIs are readable for this session even without persistable grant.
            }
            photoUri = uri.toString()
            showPhoto(uri.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_PawMap_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogJournalEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tripId = arguments?.getLong(ARG_TRIP) ?: -1L
        dayIndex = arguments?.getInt(ARG_DAY) ?: 0
        binding.tvTitle.text = arguments?.getString(ARG_TITLE).orEmpty()
        binding.tvDayLabel.text = arguments?.getString(ARG_DATE_LABEL).orEmpty()

        vm.load(tripId, dayIndex)
        vm.journal.observe(viewLifecycleOwner) { j ->
            j?.let {
                binding.etMemo.setText(it.memo ?: "")
                if (!it.photoUri.isNullOrBlank()) {
                    photoUri = it.photoUri
                    showPhoto(it.photoUri!!)
                }
            }
        }

        binding.btnClose.setOnClickListener { dismiss() }
        binding.photoBox.setOnClickListener {
            pickImage.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        binding.btnDone.setOnClickListener {
            vm.save(tripId, dayIndex, photoUri, binding.etMemo.text?.toString()) { dismiss() }
        }
    }

    private fun showPhoto(uri: String) {
        binding.photoPlaceholder.visibility = View.GONE
        binding.photoImage.visibility = View.VISIBLE
        binding.photoImage.load(uri)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TRIP = "tripId"
        private const val ARG_DAY = "dayIndex"
        private const val ARG_TITLE = "title"
        private const val ARG_DATE_LABEL = "dateLabel"

        fun newInstance(tripId: Long, dayIndex: Int, title: String, dateLabel: String): JournalEditDialogFragment =
            JournalEditDialogFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_TRIP, tripId)
                    putInt(ARG_DAY, dayIndex)
                    putString(ARG_TITLE, title)
                    putString(ARG_DATE_LABEL, dateLabel)
                }
            }
    }
}
