package com.example.materialdesignapp.model

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
) {
    Snackbar
        .make(
            this,
            text,
            Snackbar.LENGTH_INDEFINITE
        )
        .setAction(actionText) { action(this) }
        .show()
}