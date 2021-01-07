package com.arctouch.io.outdoorsychallenge.features.showqrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.arctouch.io.outdoorsychallenge.databinding.DialogShowQrcodeBinding
import com.arctouch.io.outdoorsychallenge.extensions.saveImage
import com.arctouch.io.outdoorsychallenge.extensions.shareImageBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowQrCodeDialogFragment : DialogFragment() {

    private val arguments: ShowQrCodeDialogFragmentArgs by navArgs()
    private lateinit var binding: DialogShowQrcodeBinding
    private val viewModel: ShowQrCodeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogShowQrcodeBinding
        .inflate(inflater)
        .apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ShowQrCodeDialogFragment.viewModel

            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeEvents()
    }

    private fun setupViews() = with(binding) {
        showQrCodeIv.setImageBitmap(arguments.image)
    }

    private fun observeEvents() = with(viewModel) {
        shareEvent.observe(viewLifecycleOwner) {
            viewModel.executeSaveImage(::saveImage, arguments.image)
        }

        imageSavedEvent.observe(viewLifecycleOwner) { uri -> shareImageBy(uri) }
    }
}