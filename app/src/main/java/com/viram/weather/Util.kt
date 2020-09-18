package com.viram.weather

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


object Util {
    public fun isNetworkAvailable(nContext: Context?): Boolean {
        var isNetAvailable = false
        if (nContext != null) {
            val mConnectivityManager = nContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (mConnectivityManager != null) {
                var mobileNetwork = false
                var wifiNetwork = false
                var mobileNetworkConnecetd = false
                var wifiNetworkConnecetd = false
                val mobileInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                val wifiInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mobileInfo != null) mobileNetwork = mobileInfo.isAvailable
                if (wifiInfo != null) wifiNetwork = wifiInfo.isAvailable
                if ((wifiNetwork || mobileNetwork) && wifiInfo != null) {
                    if (mobileInfo != null) mobileNetworkConnecetd = mobileInfo
                        .isConnectedOrConnecting
                    wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting
                }
                isNetAvailable = mobileNetworkConnecetd || wifiNetworkConnecetd
            }
        }
        return isNetAvailable
    }

    fun showToastLong(context: Context, message: String?) {
        val toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG)
        toast.show()
    }
    fun showDialogMessage(
        context: Context,
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


}