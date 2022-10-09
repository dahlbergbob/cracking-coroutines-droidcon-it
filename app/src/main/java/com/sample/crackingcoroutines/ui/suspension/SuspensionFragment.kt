package com.sample.crackingcoroutines.ui.suspension

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.crackingcoroutines.R

class SuspensionFragment : Fragment() {

    private val viewModel: SuspensionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_suspension, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.output.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.output).text = it
        }
        view.findViewById<Button>(R.id.suspension_button).setOnClickListener {
            viewModel.pauseAndResume()
        }
        view.findViewById<Button>(R.id.order_button).setOnClickListener {
            viewModel.order()
        }
        view.findViewById<Button>(R.id.simple_continuation_button).setOnClickListener {
            viewModel.simpleContinuation()
        }
        view.findViewById<Button>(R.id.continuation_button_callback).setOnClickListener {
            viewModel.suspendingCallbacks()
        }
    }

    companion object {
        fun newInstance() = SuspensionFragment()
    }
}