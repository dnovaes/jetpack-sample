package com.arctouch.io.outdoorsychallenge.features.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentQrcodeBinding
import com.arctouch.io.outdoorsychallenge.features.main.OutdoorsyViewModel
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QrCodeFragment : ErrorHandlingFragment(), BarcodeCallback {

    override lateinit var binding: FragmentQrcodeBinding
    override val viewModel: QrCodeViewModel by viewModel()
    override val navController: NavController by lazy { findNavController() }
    private val sharedViewModel: OutdoorsyViewModel by sharedViewModel()

    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentQrcodeBinding.inflate(inflater).apply {
        lifecycleOwner = viewLifecycleOwner

        binding = this
    }.root

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    private fun setupViews() = with(binding) {
        barcodeView = qrCodeReader
        barcodeView.decodeContinuous(this@QrCodeFragment)
    }

    override fun barcodeResult(result: BarcodeResult?) {
        result?.let {
            barcodeView.pause()
            navController.popBackStack(R.id.search_rv, false)
            sharedViewModel.onQrCodeRead(it.text)
        } ?: Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
    }
}
