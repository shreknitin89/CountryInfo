package com.example.countryinfo.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.countryinfo.R

fun showAlert(
    context: Context,
    title: String,
    message: String,
    positiveButton: String = context.getString(R.string.alert_button_ok),
    positiveButtonClickListener: DialogInterface.OnClickListener? = null,
    negativeButton: String = context.getString(R.string.alert_button_cancel),
    negativeButtonClickListener: DialogInterface.OnClickListener? = null,
) = AlertDialog.Builder(context)
    .setTitle(title)
    .setMessage(message)
    .setPositiveButton(positiveButton, positiveButtonClickListener)
    .setNegativeButton(negativeButton, negativeButtonClickListener)
    .create()