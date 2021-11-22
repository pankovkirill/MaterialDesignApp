package com.example.materialdesignapp.view

import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.example.materialdesignapp.R
import com.example.materialdesignapp.databinding.FragmentHomeBinding
import com.example.materialdesignapp.model.PODServerResponseData
import com.example.materialdesignapp.model.hide
import com.example.materialdesignapp.model.show
import com.example.materialdesignapp.viewmodel.AppState
import com.example.materialdesignapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val date = Calendar.getInstance()
    private val year = date.get(Calendar.YEAR)
    private val month = date.get(Calendar.MONTH) + 1
    private val day = date.get(Calendar.DAY_OF_MONTH)

    private var isExpanded = false

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

    @RequiresApi(Build.VERSION_CODES.P)
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
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                container, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            with(binding) {
                val params: ViewGroup.LayoutParams = imageView.layoutParams
                params.height =
                    if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
                imageView.layoutParams = params
                imageView.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }
        binding.errorTextView.setOnClickListener {
            ObjectAnimator.ofFloat(binding.errorTextView, "translationY", -100f).start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    imageView.show()
                    loadingLayout.hide()
                }
                setData(appState.serverResponseData)
            }
            is AppState.Loading -> {
                with(binding) {
                    imageView.hide()
                    loadingLayout.show()
                }
                setError()
            }
            is AppState.Error -> {
                with(binding) {
                    imageView.hide()
                    loadingLayout.hide()
                    Toast.makeText(
                        context,
                        "Отсутствует подключение к интернету",
                        Toast.LENGTH_SHORT
                    ).show()
                    setError()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setData(serverResponseData: PODServerResponseData) {
        with(binding) {
            imageView.show()
            loadingLayout.hide()
            imageView.load(serverResponseData.url)
            bottom_sheet_description_header.text = titleSpan(serverResponseData.title)
            bottom_sheet_description.text = textSpan(serverResponseData.explanation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun textSpan(text: String):Spannable{
        val spannable = SpannableString(text)

        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            ),
            0,
            100,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun titleSpan(text: String):Spannable{
        val symbolCount = text.length
        val spannable = SpannableString(text)
        spannable.setSpan(
            BackgroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange_700
                )
            ),
            0,
            symbolCount,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            BulletSpan(15,ContextCompat.getColor(requireContext(), R.color.orange_700), 10),
            0,
            1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setError() {
        if (!checkInternet())
            ObjectAnimator.ofFloat(binding.errorTextView, "translationY", 50f).start()
        else
            ObjectAnimator.ofFloat(binding.errorTextView, "translationY", -100f).start()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}