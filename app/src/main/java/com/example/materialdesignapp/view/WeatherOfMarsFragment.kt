package com.example.materialdesignapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.materialdesignapp.R
import com.example.materialdesignapp.databinding.PhotoOfMarsFragmentBinding
import com.example.materialdesignapp.databinding.WeatherOfMarsFragmentBinding
import com.example.materialdesignapp.viewmodel.PhotoOfMarsViewModel
import com.example.materialdesignapp.viewmodel.WeatherOfMarsViewModel

class WeatherOfMarsFragment : Fragment() {

    private lateinit var weatherOfMarsViewModel: WeatherOfMarsViewModel
    private var _binding: WeatherOfMarsFragmentBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: WeatherOfMarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherOfMarsViewModel =
            ViewModelProvider(this).get(WeatherOfMarsViewModel::class.java)

        _binding = WeatherOfMarsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textWeather
        weatherOfMarsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}