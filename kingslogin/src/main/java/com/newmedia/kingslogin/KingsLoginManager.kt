package com.newmedia.kingslogin

import android.app.Activity
import android.content.Intent
import com.newmedia.kingslogin.impl.CallbackManagerImpl
import com.newmedia.kingslogin.model.KcScopes

class KingsLoginManager internal constructor() {

    fun registerCallback(
            callbackManager: CallbackManager,
            callback: KingsLoginCallback) {
        if (callbackManager !is CallbackManagerImpl) {
            throw KingsLoginException("Unexpected CallbackManager, " + "please use the provided Factory.")
        }
        callbackManager.registerCallback(
                CallbackManagerImpl.RequestCodeOffset.Login.requestCode(),
                object : CallbackManagerImpl.Callback {
                    override fun onActivityResult(resultCode: Int, data: Intent?): Boolean {
                        return this@KingsLoginManager.onActivityResult(
                                resultCode,
                                data,
                                callback)
                    }
                }
        )
    }

    fun unregisterCallback(
            callbackManager: CallbackManager) {
        if (callbackManager !is CallbackManagerImpl) {
            throw KingsLoginException("Unexpected CallbackManager, " + "please use the provided Factory.")
        }
        callbackManager.unregisterCallback(
                CallbackManagerImpl.RequestCodeOffset.Login.requestCode())
    }

    internal fun onActivityResult(resultCode: Int, data: Intent?, callback: KingsLoginCallback): Boolean {
        return when(resultCode) {
            Activity.RESULT_OK -> {
                val token = data!!.getStringExtra("code")
                val acceptedScopes = data.getStringArrayListExtra("accepted_scopes")
                val rejectedScopes = data.getStringArrayListExtra("rejected_scopes")
                callback.onSuccess(token, KcScopes(acceptedScopes, rejectedScopes))
                true
            }
            Activity.RESULT_CANCELED -> {
                callback.onCancel()
                true
            }
            Activity.RESULT_FIRST_USER -> {
                callback.onError(KingsLoginException(data?.getStringExtra("error_message")?: "No response for KingsChat"))
                true
            }
            else -> {
                callback.onError(KingsLoginException(data?.getStringExtra("error_message")?: "No response for KingsChat"))
                true
            }
        }

    }

    companion object {

        @Volatile
        private var instance: KingsLoginManager? = null

        fun getInstance(): KingsLoginManager {
            if (instance == null) {
                synchronized(KingsLoginManager::class.java) {
                    if (instance == null) {
                        instance = KingsLoginManager()
                    }
                    return instance!!
                }
            } else {
                return instance!!
            }
        }
    }

}
