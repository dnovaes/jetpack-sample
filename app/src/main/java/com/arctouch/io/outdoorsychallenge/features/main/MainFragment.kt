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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.R.string.main_qr_code_error_message
import com.arctouch.io.outdoorsychallenge.R.string.main_voice_search_progress_text
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
                getString(R.string.main_tab_all),
                getString(R.string.main_tab_favorites),
                getString(R.string.main_tab_qr_code_result)
            )
        )

        mainVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == FAVORITES_TAB_POSITION)
                    this@MainFragment.viewModel.onFavoritesTabSelected()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
    }

    private fun observeEvents() {
        sharedViewModel.qrCodeEvent.observe(viewLifecycleOwner) {
            binding.mainVp.currentItem = QR_CODE_RESULT_TAB_POSITION
            viewModel.onQrCodeListReceived(it)
        }

        viewModel.qrCodeGeneratedEvent.observe(viewLifecycleOwner) {
            showQrCodeDialog(it)
        }
    }

    private fun startVoiceRecognitionActivity() {
        val intent = Intent(ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(EXTRA_PROMPT, getString(main_voice_search_progress_text))
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
    }

    private fun searchQuery(query: String) {
        binding.mainTiet.setText(query)
        viewModel.onSearchRvButtonClicked()
    }

    private fun showQrCodeOptionsDialog(context: Context) {
        qrCodeDialog ?: MaterialAlertDialogBuilder(context).run {
            setTitle(getString(R.string.main_qr_code_dialog_title))
            setPositiveButton(getString(R.string.main_qr_code_dialog_in_app_button)) { _, _ -> navigateToReadQrCode() }
            setNegativeButton(getString(R.string.main_qr_code_dialog_external_button)) { _, _ ->
                IntentIntegrator(activity).apply {
                    setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                    setBeepEnabled(false)
                    initiateScan()
                }
            }
            setNeutralButton(R.string.main_qr_code_dialog_generate_button) { _, _ ->
                qrCodeDialog?.dismiss()
                viewModel.onGenerateQrCodeButtonClicked()
            }
            qrCodeDialog = create()
        }
        qrCodeDialog?.show()
    }

    private fun showQrCodeDialog(jsonText: String?) {
        if (jsonText.isNullOrBlank()) {
            Toast.makeText(context, getString(main_qr_code_error_message), Toast.LENGTH_LONG).show()
            return
        }

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
        private const val FAVORITES_TAB_POSITION = 1
        private const val QR_CODE_RESULT_TAB_POSITION = 2

        private const val VOICE_RECOGNITION_REQUEST_CODE = 1234
    }
}
