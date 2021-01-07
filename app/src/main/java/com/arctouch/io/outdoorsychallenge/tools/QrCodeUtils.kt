package com.arctouch.io.outdoorsychallenge.tools

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import timber.log.Timber

object QrCodeUtils {

    const val MAX_CHAR_COUNT = 2953

    fun generateQRCodeBitmapBy(text: String, size: Int): Bitmap {
        val width = size
        val height = size
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width)
                for (y in 0 until height)
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        } catch (e: WriterException) {
            Timber.tag("QrCodeUtils").d( "QRCode generation error: ${e.message}")
        }
        return bitmap
    }
}
