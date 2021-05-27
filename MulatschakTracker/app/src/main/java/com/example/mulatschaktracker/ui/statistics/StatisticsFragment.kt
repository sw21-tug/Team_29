package com.example.mulatschaktracker.ui.statistics
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mulatschaktracker.R

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: GameFinishedViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
                ViewModelProvider(this).get(GameFinishedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_statistics, container, false)
        return root
    }
}