package com.arctouch.io.outdoorsychallenge.features.searchrv

import android.animation.LayoutTransition
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.OutdoorsyGraphDirections
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.R.string.search_rv_voice_search_progress_text
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentSearchRvBinding
import com.arctouch.io.outdoorsychallenge.extensions.getScreenWidth
import com.arctouch.io.outdoorsychallenge.extensions.hideKeyboard
import com.arctouch.io.outdoorsychallenge.features.main.OutdoorsyViewModel
import com.arctouch.io.outdoorsychallenge.features.searchrv.adapters.SearchRvVehicleAdapter
import com.arctouch.io.outdoorsychallenge.features.showqrcode.ShowQrCodeDialog
import com.arctouch.io.outdoorsychallenge.tools.QrCodeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRvFragment : ErrorHandlingFragment() {

    override lateinit var binding: FragmentSearchRvBinding
    override val viewModel: SearchRvViewModel by viewModel()
    override val navController by lazy { findNavController() }
    private val sharedViewModel: OutdoorsyViewModel by sharedViewModel()

    private lateinit var vehicleAdapter: SearchRvVehicleAdapter
    private var qrCodeDialog: AlertDialog? = null

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

    private fun navigateToQrCode() {
        navController.navigate(OutdoorsyGraphDirections.actionGlobalQrcode())
    }

    private fun setupViews() = with(binding) {
        qrCodeBt.setOnClickListener { context?.let { setupQrCodeDialog(it) } }

        binding.srlSearchRvResults.apply {
            setOnRefreshListener { this@SearchRvFragment.viewModel.onSwipeToRefresh() }
            setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary)
            layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)
        }

        tietSearchRv.setOnEditorActionListener { _, _, _ ->
            hideKeyboard(root)
            this@SearchRvFragment.viewModel.onSearchRvButtonClicked()
            true
        }

        tilSearchRv.setEndIconOnClickListener { startVoiceRecognitionActivity() }

        vehicleAdapter = SearchRvVehicleAdapter(viewLifecycleOwner)
        rvSearchRvResults.adapter = vehicleAdapter
    }

    private fun observeEvents() = with(viewModel) {
        vehicles.observe(viewLifecycleOwner) { vehicleAdapter.submitList(it) }

        progressIsVisible.observe(viewLifecycleOwner) {
            binding.srlSearchRvResults.isRefreshing = it
        }

        sharedViewModel.qrCodeEvent.observe(viewLifecycleOwner) {
            binding.tietSearchRv.setText(QR_CODE_RESULT)
            viewModel.onQrCodeListReceived()
        }
    }

    private fun startVoiceRecognitionActivity() {
        val intent = Intent(ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(EXTRA_PROMPT, getString(search_rv_voice_search_progress_text))
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
    }

    private fun searchQuery(query: String) {
        binding.tietSearchRv.setText(query)
        viewModel.onSearchRvButtonClicked()
    }

    private fun setupQrCodeDialog(context: Context) {
        qrCodeDialog ?: MaterialAlertDialogBuilder(context).run {
            setTitle(getString(R.string.qrcode_dialog_title))
            setPositiveButton(getString(R.string.qrcode_in_app)) { _, _ -> navigateToQrCode() }
            setNegativeButton(getString(R.string.qrcode_external)) { _, _ ->
                IntentIntegrator(activity).apply {
                    setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                    setBeepEnabled(false)
                    initiateScan()
                }
            }
            setNeutralButton(R.string.qrcode_generation) { _, _ ->
                qrCodeDialog?.dismiss()
                showQrCodeDialog(context)
            }
            qrCodeDialog = create()
        }
        qrCodeDialog
            ?.getButton(AlertDialog.BUTTON_NEUTRAL)
            ?.isEnabled = binding.tietSearchRv.text.toString().isNotBlank()
        qrCodeDialog?.show()
    }

    private fun showQrCodeDialog(context: Context) {
        val jsonText = viewModel.getResultJson()
        if (jsonText.isBlank()) return

        val qrCodeBitmap = QrCodeUtils.generateQRCodeBitmapBy(jsonText, getScreenWidth())

        ShowQrCodeDialog.Builder(context, R.style.Theme_AppCompat_Dialog).apply {
            setImageBitmapSrc(qrCodeBitmap)
            create()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            val matches = data?.getStringArrayListExtra(EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) searchQuery(matches[0])
        }
    }

    companion object {

        private const val VOICE_RECOGNITION_REQUEST_CODE = 1234
        const val QR_CODE_RESULT = "QrCode Result"
    }
}
