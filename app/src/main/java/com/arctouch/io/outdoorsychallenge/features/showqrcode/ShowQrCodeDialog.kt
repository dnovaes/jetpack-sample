package com.arctouch.io.outdoorsychallenge.features.showqrcode

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import com.arctouch.io.outdoorsychallenge.databinding.DialogShowQrcodeBinding

class ShowQrCodeDialog private constructor(context: Context, @StyleRes private val style: Int) :
    Dialog(context, style) {

    private val binding: DialogShowQrcodeBinding by lazy {
        DialogShowQrcodeBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun setImageBitmapSrc(bitmap: Bitmap?) {
        binding.showQrCode.setImageBitmap(bitmap)
    }

    class Builder(private val context: Context, @StyleRes private val style: Int) {

        private var imageBitmap: Bitmap? = null

        fun setImageBitmapSrc(bitmap: Bitmap) {
            imageBitmap = bitmap
        }

        fun create() = ShowQrCodeDialog(context, style).apply {
            setImageBitmapSrc(imageBitmap)
        }

        fun show(): ShowQrCodeDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }
    }
}