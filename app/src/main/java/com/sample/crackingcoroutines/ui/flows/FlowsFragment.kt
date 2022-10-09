package com.sample.crackingcoroutines.ui.flows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.crackingcoroutines.R
import com.sample.crackingcoroutines.ui.jobs.JobsViewModel

class FlowsFragment : Fragment() {

    private val viewModel: FlowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_flows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.output.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.output).text = it
        }
        view.findViewById<Button>(R.id.flows_on_button).setOnClickListener {
            viewModel.flowsOn()
        }
        view.findViewById<Button>(R.id.launching_in_button).setOnClickListener {
            viewModel.launchingIn()
        }
        view.findViewById<Button>(R.id.catching_button).setOnClickListener {
            viewModel.catching()
        }
        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            viewModel.cancelFlow()
        }
    }

    companion object {
        fun newInstance() = FlowsFragment()
    }
}