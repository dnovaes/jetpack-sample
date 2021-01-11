package com.arctouch.io.outdoorsychallenge.features.searchrv

import android.animation.LayoutTransition
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentSearchRvBinding
import com.arctouch.io.outdoorsychallenge.features.main.MainViewModel
import com.arctouch.io.outdoorsychallenge.features.outdoorsy.OutdoorsyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRvFragment : ErrorHandlingFragment() {

    override lateinit var binding: FragmentSearchRvBinding
    override val viewModel: SearchRvViewModel by viewModel()
    override val navController by lazy { findNavController() }
    private val outdoorsyViewModel: OutdoorsyViewModel by sharedViewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var vehicleAdapter: SearchRvVehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchRvBinding.inflate(inflater).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@SearchRvFragment.viewModel

        binding = this
    }.root

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeEvents()
    }

    private fun setupViews() = with(binding) {
        binding.searchRvResultsSrl.apply {
            setOnRefreshListener { this@SearchRvFragment.viewModel.onSwipeToRefresh() }
            setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary)
            layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)
        }

        vehicleAdapter = SearchRvVehicleAdapter(viewLifecycleOwner)
        searchRvResultsRv.adapter = vehicleAdapter
    }

    private fun observeEvents() {
        mainViewModel.searchButtonClickEvent.observe(viewLifecycleOwner) {
            viewModel.onSearchRvButtonClicked(it)
        }

        viewModel.vehicles.observe(viewLifecycleOwner) { vehicleAdapter.submitList(it) }

        viewModel.progressIsVisible.observe(viewLifecycleOwner) {
            binding.searchRvResultsSrl.isRefreshing = it
        }
    }

    companion object {

        fun newInstance() = SearchRvFragment()
    }
}
