package com.arctouch.io.outdoorsychallenge.extensions

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.arctouch.io.outdoorsychallenge.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val EXTERNAL_CACHE_PATH = "outdoorsy_images"
private const val SAVED_IMAGE_NAME = "qrcode_image.png"
private const val SHARED_IMAGE_QUALITY = 100

fun Fragment.showWarningSnackbar(anchorView: View, @StringRes actionTextRes: Int) =
    showSnackbar(
        anchorView,
        resources.getString(actionTextRes),
        context?.getDrawable(R.drawable.shape_warning_snackbar_background)
    )

private fun showSnackbar(anchorView: View, actionText: String, background: Drawable?) =
    Snackbar
        .make(anchorView, actionText, Snackbar.LENGTH_LONG)
        .apply {
            setActionTextColor(context.getColor(R.color.black))
            view.background = background
            show()
        }

fun Fragment.hideKeyboard(view: View) =
    context?.inputMethodService?.hideSoftInputFromWindow(view.windowToken, 0)

fun Fragment.getScreenWidth() = DisplayMetrics().let { displayMetrics ->
    activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    displayMetrics.widthPixels
}

fun Fragment.saveImage(image: Bitmap): Uri? {
    val context = context ?: return null
    val cachePath = File(context.externalCacheDir, EXTERNAL_CACHE_PATH).also { it.mkdirs() }
    val file = File(cachePath, SAVED_IMAGE_NAME)

    try {
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, SHARED_IMAGE_QUALITY, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        Timber.tag("LocalFileProvider").d("Write file for sharing error: ${e.message}")
        e.printStackTrace()
    }

    return FileProvider.getUriForFile(context, context.packageName + ".provider", file)
}

fun Fragment.shareImageBy(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    startActivity(intent)
}