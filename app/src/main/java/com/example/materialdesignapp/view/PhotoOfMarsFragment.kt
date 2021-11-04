package com.example.materialdesignapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.materialdesignapp.databinding.PhotoOfMarsFragmentBinding
import com.example.materialdesignapp.viewmodel.AppState
import com.example.materialdesignapp.viewmodel.PhotoOfMarsViewModel

class PhotoOfMarsFragment : Fragment() {

    private var _binding: PhotoOfMarsFragmentBinding? = null
    private val binding get() = _binding!!

    private val photoOfMarsAdapter = PhotoOfMarsAdapter()

    private val viewModel: PhotoOfMarsViewModel by lazy {
        ViewModelProvider(this).get(PhotoOfMarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoOfMarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPhotoOfMars.adapter = photoOfMarsAdapter

        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getData()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {

            }
            is AppState.SuccessPhoto -> {
                photoOfMarsAdapter.setData(appState.serverResponseData.photos)
            }
            is AppState.Error -> {
                Toast.makeText(context, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}