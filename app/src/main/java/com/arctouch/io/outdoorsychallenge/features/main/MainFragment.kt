package com.arctouch.io.outdoorsychallenge.features.main

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.R.string.search_rv_voice_search_progress_text
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingFragment
import com.arctouch.io.outdoorsychallenge.databinding.FragmentMainBinding
import com.arctouch.io.outdoorsychallenge.extensions.getScreenWidth
import com.arctouch.io.outdoorsychallenge.extensions.hideKeyboard
import com.arctouch.io.outdoorsychallenge.features.outdoorsy.OutdoorsyViewModel
import com.arctouch.io.outdoorsychallenge.tools.QrCodeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : ErrorHandlingFragment() {

    override lateinit var binding: FragmentMainBinding
    override val viewModel: MainViewModel by sharedViewModel()
    override val navController by lazy { findNavController() }
    private val sharedViewModel: OutdoorsyViewModel by sharedViewModel()

    private var qrCodeDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@MainFragment.viewModel

        binding = this
    }.root

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeEvents()
    }

    private fun navigateToReadQrCode() {
        navController.navigate(MainFragmentDirections.actionMainToReadQrCode())
    }

    private fun navigateToShowQrCode(image: Bitmap) {
        navController.navigate(MainFragmentDirections.actionMainToShowQrCode(image))
    }

    private fun setupViews() = with(binding) {
        mainQrCodeBt.setOnClickListener { context?.let { showQrCodeOptionsDialog(it) } }

        mainTiet.setOnEditorActionListener { _, _, _ ->
            hideKeyboard(root)
            mainVp.currentItem = SEARCH_RESULT_TAB_POSITION
            this@MainFragment.viewModel.onSearchRvButtonClicked()
            true
        }

        mainTil.setEndIconOnClickListener { startVoiceRecognitionActivity() }

        mainVp.adapter = MainPagerAdapter(
            fragmentManager = childFragmentManager,
            titles = listOf(
                getString(R.string.all),
                getString(R.string.favorites),
                getString(R.string.qr_code_result)
            )
        )
    }

    private fun observeEvents() {
        sharedViewModel.qrCodeEvent.observe(viewLifecycleOwner) {
            binding.mainVp.currentItem = QR_CODE_RESULT_TAB_POSITION
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
        binding.mainTiet.setText(query)
        viewModel.onSearchRvButtonClicked()
    }

    private fun showQrCodeOptionsDialog(context: Context) {
        qrCodeDialog ?: MaterialAlertDialogBuilder(context).run {
            setTitle(getString(R.string.qr_code_dialog_title))
            setPositiveButton(getString(R.string.qr_code_in_app)) { _, _ -> navigateToReadQrCode() }
            setNegativeButton(getString(R.string.qr_code_external)) { _, _ ->
                IntentIntegrator(activity).apply {
                    setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                    setBeepEnabled(false)
                    initiateScan()
                }
            }
            setNeutralButton(R.string.qr_code_generation) { _, _ ->
                qrCodeDialog?.dismiss()
                showQrCodeDialog()
            }
            qrCodeDialog = create()
        }
        qrCodeDialog
            ?.getButton(AlertDialog.BUTTON_NEUTRAL)
            ?.isEnabled = binding.mainTiet.text.toString().isNotBlank()
        qrCodeDialog?.show()
    }

    private fun showQrCodeDialog() {
        val jsonText = viewModel.getResultJson()
        if (jsonText.isBlank()) return

        navigateToShowQrCode(QrCodeUtils.generateQRCodeBitmapBy(jsonText, getScreenWidth()))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            val matches = data?.getStringArrayListExtra(EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) searchQuery(matches[0])
        }
    }

    companion object {

        private const val SEARCH_RESULT_TAB_POSITION = 0
        private const val QR_CODE_RESULT_TAB_POSITION = 2

        private const val VOICE_RECOGNITION_REQUEST_CODE = 1234
    }
}
