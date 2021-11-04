package com.example.materialdesignapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherOfMarsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Weather"
    }
    val text: LiveData<String> = _text
}