package com.newmedia.kingslogin.helper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.Fragment
import com.newmedia.kingslogin.KingsLoginException
import com.newmedia.kingslogin.R

import java.util.ArrayList

object KingsLogin {

    private var kcPackageName: String = ""
    private var applicationClientId: String? = null

    internal const val KINGSLOGIN_REQUEST_CODE = 42

    fun init(context: Context) {
        val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        applicationClientId = applicationInfo.metaData.getString(context.getString(R.string.app_meta_data))
        kcPackageName =  context.getString(R.string.kc_package_name)
    }

    private fun isAppInstalled(context: Context?): Boolean {
        return try {
            context?.packageManager?.getApplicationInfo(kcPackageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun requestPermissions(activity: Activity, scopes: List<String>) {
        requestPermissions(activity, null, scopes)
    }

    fun requestPermissions(fragment: Fragment, scopes: List<String>) {
        requestPermissions(null, fragment, scopes)
    }

    fun requestPermissions(context: Context? = null, fragment: Fragment? = null, scopes: List<String>) {
        val privateContext: Context = when {
            applicationClientId == null -> throw KingsLoginException("KingsLogin not initialized")
            context != null && context is Activity -> context
            fragment != null -> fragment.context!!
            else -> throw KingsLoginException("Activity or fragment must be set")
        }

        val currentActivity = getActivity(privateContext)
        if (isAppInstalled(currentActivity)) {
            val intent = loginIntent(*scopes.toTypedArray())
            if (canHandleIntent(intent, privateContext)) {
                if (fragment != null) {
                    fragment.startActivityForResult(intent, KINGSLOGIN_REQUEST_CODE)
                } else {
                    currentActivity?.startActivityForResult(intent, KINGSLOGIN_REQUEST_CODE)
                }
            } else {
                startGooglePlayActivity(currentActivity)
            }
        } else {
            startGooglePlayActivity(currentActivity)
        }
    }

    private fun getActivity(baseContext: Context): Activity? {
        var context = baseContext
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    private fun loginIntent(vararg scopes: String): Intent =
            Intent("$kcPackageName.AUTHORIZE_APP")
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .putStringArrayListExtra("scopes", ArrayList(scopes.asList()))
                    .putExtra("clientId", applicationClientId)

    private fun startGooglePlayActivity(activity: Activity?) =
            try {
                activity?.startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$kcPackageName")))
            } catch (e: android.content.ActivityNotFoundException) {
                activity?.startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$kcPackageName")))
            }

    private fun canHandleIntent(intent: Intent, context: Context): Boolean = (context.packageManager.queryIntentActivities(intent, 0)?.size) ?: 0 > 0

}
