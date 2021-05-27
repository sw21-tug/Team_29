package com.example.mulatschaktracker.ui.tipps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mulatschaktracker.R

class TippsFragment : Fragment() {

    private lateinit var tippsViewModel: TippsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        tippsViewModel =
                ViewModelProvider(this).get(TippsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tipps, container, false)
        return root
    }
}