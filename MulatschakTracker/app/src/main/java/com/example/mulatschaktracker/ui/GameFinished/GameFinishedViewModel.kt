package com.example.mulatschaktracker.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameFinishedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Statistics Fragment"
    }
    val text: LiveData<String> = _text
}