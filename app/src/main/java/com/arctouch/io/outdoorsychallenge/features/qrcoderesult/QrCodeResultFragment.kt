package com.arctouch.io.outdoorsychallenge.features.qrcoderesult

import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentQrCodeResultBinding
import com.arctouch.io.outdoorsychallenge.features.main.MainViewModel
import com.arctouch.io.outdoorsychallenge.features.outdoorsy.OutdoorsyViewModel
import com.arctouch.io.outdoorsychallenge.features.vehicleadapter.VehicleAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QrCodeResultFragment : ErrorHandlingFragment() {

    override lateinit var binding: FragmentQrCodeResultBinding
    override val viewModel: QrCodeResultViewModel by viewModel()
    override val navController by lazy { findNavController() }
    private val outdoorsyViewModel: OutdoorsyViewModel by sharedViewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var vehicleAdapter: VehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentQrCodeResultBinding.inflate(inflater).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@QrCodeResultFragment.viewModel

        binding = this
    }.root

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeEvents()
    }

    private fun setupViews() = with(binding) {
        vehicleAdapter = VehicleAdapter(viewLifecycleOwner)
        qrCodeResultsRv.adapter = vehicleAdapter
    }

    private fun observeEvents() = with(viewModel) {
        vehicles.observe(viewLifecycleOwner) { vehicleAdapter.submitList(it) }
    }

    companion object {

        fun newInstance() = QrCodeResultFragment()
    }
}
