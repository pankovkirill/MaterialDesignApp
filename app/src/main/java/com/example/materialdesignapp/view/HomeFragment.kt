package com.example.materialdesignapp.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.materialdesignapp.R
import com.example.materialdesignapp.databinding.FragmentHomeBinding
import com.example.materialdesignapp.model.PODServerResponseData
import com.example.materialdesignapp.model.hide
import com.example.materialdesignapp.model.show
import com.example.materialdesignapp.model.showSnackBar
import com.example.materialdesignapp.viewmodel.AppState
import com.example.materialdesignapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_home.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val date = Calendar.getInstance()
    private val year = date.get(Calendar.YEAR)
    private val month = date.get(Calendar.MONTH) + 1
    private val day = date.get(Calendar.DAY_OF_MONTH)

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getData("${year}-${month}-${day - 1}")
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                viewModel.getData("${year}-${month}-${day - position}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    mainView.show()
                    loadingLayout.hide()
                }
                setData(appState.serverResponseData)
            }
            is AppState.Loading -> {
                with(binding) {
                    mainView.hide()
                    loadingLayout.show()
                }
            }
            is AppState.Error -> {
                with(binding) {
                    mainView.hide()
                    loadingLayout.show()
                    mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.getData("${year}-${month}-${day - 1}") })
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(serverResponseData: PODServerResponseData) {
        with(binding) {
            mainView.show()
            loadingLayout.hide()
            imageView.load(serverResponseData.url)
            bottom_sheet_description_header.text = serverResponseData.title
            bottom_sheet_description.text = serverResponseData.explanation
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}