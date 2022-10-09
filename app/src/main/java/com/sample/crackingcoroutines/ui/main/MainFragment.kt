package com.sample.crackingcoroutines.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.crackingcoroutines.R
import com.sample.crackingcoroutines.ui.exceptions.ExceptionsFragment
import com.sample.crackingcoroutines.ui.flows.FlowsFragment
import com.sample.crackingcoroutines.ui.jobs.JobsFragment
import com.sample.crackingcoroutines.ui.structure.StructureFragment
import com.sample.crackingcoroutines.ui.suspension.SuspensionFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.suspension).setOnClickListener(::navigate)
        view.findViewById<Button>(R.id.structure).setOnClickListener(::navigate)
        view.findViewById<Button>(R.id.jobs).setOnClickListener(::navigate)
        view.findViewById<Button>(R.id.exceptions).setOnClickListener(::navigate)
        view.findViewById<Button>(R.id.flows).setOnClickListener(::navigate)
    }

    private fun navigate(view: View) {
        val fragment = when(view.id) {
            R.id.suspension -> SuspensionFragment.newInstance()
            R.id.structure -> StructureFragment.newInstance()
            R.id.jobs -> JobsFragment.newInstance()
            R.id.exceptions -> ExceptionsFragment.newInstance()
            R.id.flows -> FlowsFragment.newInstance()
            else -> throw IllegalArgumentException("No view to navigate to")
        }
        Log.d("###", "Fragment -> ${fragment.toString()}")
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, fragment.toString())
            .addToBackStack(fragment.toString())
            .commitAllowingStateLoss()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}