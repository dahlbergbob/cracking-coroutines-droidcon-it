package com.sample.crackingcoroutines.ui.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.crackingcoroutines.R
import com.sample.crackingcoroutines.ui.structure.StructureViewModel

class JobsFragment : Fragment() {

    private val viewModel: JobsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.output.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.output).text = it
        }
        view.findViewById<Button>(R.id.lifecycle_button).setOnClickListener {
            viewModel.jobsLifeCycle()
        }
        view.findViewById<Button>(R.id.outer_button).setOnClickListener {
            viewModel.outerJob()
        }
        view.findViewById<Button>(R.id.joining_button).setOnClickListener {
            viewModel.joining()
        }
        view.findViewById<Button>(R.id.cancelling_button).setOnClickListener {
            viewModel.cancelling()
        }
        view.findViewById<Button>(R.id.cancelling_prop_button).setOnClickListener {
            viewModel.cancellingPropagation()
        }
        view.findViewById<Button>(R.id.cancelling_tidy_button).setOnClickListener {
            viewModel.cancellingHandling()
        }
        view.findViewById<Button>(R.id.cancelling_non_suspending_button).setOnClickListener {
            viewModel.cancellingNonSuspending()
        }
        view.findViewById<Button>(R.id.cancelling_scope_button).setOnClickListener {
            viewModel.cancellingScope()
        }
    }

    companion object {
        fun newInstance() = JobsFragment()
    }
}