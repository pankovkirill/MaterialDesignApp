package com.example.materialdesignapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.bottom_sheet_home.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getData()
    }

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
                        { viewModel.getData() })
                }
            }
        }
    }

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