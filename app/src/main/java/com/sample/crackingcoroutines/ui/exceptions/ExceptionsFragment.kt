package com.sample.crackingcoroutines.ui.exceptions

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

class ExceptionsFragment : Fragment() {

    private val viewModel: ExceptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_exceptions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.output.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.output).text = it
        }
        view.findViewById<Button>(R.id.exceptions_button).setOnClickListener {
            viewModel.exceptionPropagationHierarchy()
        }
        view.findViewById<Button>(R.id.supervisor_button).setOnClickListener {
            viewModel.supervisorJob()
        }
        view.findViewById<Button>(R.id.tidying_suspension_button).setOnClickListener {
            viewModel.tidyingUpSuspending()
        }
        view.findViewById<Button>(R.id.async_button).setOnClickListener {
            viewModel.asyncExceptions()
        }
    }

    companion object {
        fun newInstance() = ExceptionsFragment()
    }
}