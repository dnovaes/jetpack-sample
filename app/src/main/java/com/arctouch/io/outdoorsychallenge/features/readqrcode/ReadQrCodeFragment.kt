package com.arctouch.io.outdoorsychallenge.features.readqrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentReadQrcodeBinding
import com.arctouch.io.outdoorsychallenge.features.main.OutdoorsyViewModel
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ReadQrCodeFragment : ErrorHandlingFragment(), BarcodeCallback {

    override lateinit var binding: FragmentReadQrcodeBinding
    override val viewModel: ReadQrCodeViewModel by viewModel()
    override val navController: NavController by lazy { findNavController() }
    private val sharedViewModel: OutdoorsyViewModel by sharedViewModel()

    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentReadQrcodeBinding.inflate(inflater).apply {
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
        barcodeView.decodeContinuous(this@ReadQrCodeFragment)
    }

    override fun barcodeResult(result: BarcodeResult?) {
        result?.let {
            if (it.text[0] == '[') {
                barcodeView.pause()
                navController.popBackStack(R.id.search_rv, false)
                sharedViewModel.onQrCodeRead(it.text)
            } else {
                Timber.tag("QrCodeUtils").d( "QRCode reading error: ${it.text}")
            }
        } ?: Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
    }
}
