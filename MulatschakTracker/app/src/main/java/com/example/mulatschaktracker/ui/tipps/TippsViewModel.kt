package com.example.mulatschaktracker.ui.tipps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TippsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tipps Fragment"
    }
    val text: LiveData<String> = _text
}