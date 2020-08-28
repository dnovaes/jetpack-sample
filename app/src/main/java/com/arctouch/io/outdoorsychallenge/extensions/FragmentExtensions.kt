package com.arctouch.io.outdoorsychallenge.extensions

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.arctouch.io.outdoorsychallenge.R
import com.google.android.material.snackbar.Snackbar

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
