package com.sample.crackingcoroutines.ui.structure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.crackingcoroutines.R

class StructureFragment : Fragment() {

    private val viewModel: StructureViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_structure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.output.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.output).text = it
        }
        view.findViewById<Button>(R.id.awaiting_button).setOnClickListener {
            viewModel.awaitingChildren()
        }
        view.findViewById<Button>(R.id.still_awaiting_button).setOnClickListener {
            viewModel.stillAwaiting()
        }
        view.findViewById<Button>(R.id.still_awaiting_different_button).setOnClickListener {
            viewModel.awaitingDifferentScopes()
        }
        view.findViewById<Button>(R.id.awaiting_jobs_button).setOnClickListener {
            viewModel.awaitingDifferentJobs()
        }
        view.findViewById<Button>(R.id.awaiting_jobs2_button).setOnClickListener {
            viewModel.awaitingManualJobs()
        }
    }

    companion object {
        fun newInstance() = StructureFragment()
    }
}