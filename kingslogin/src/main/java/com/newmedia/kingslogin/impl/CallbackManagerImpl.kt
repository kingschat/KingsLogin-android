package com.newmedia.kingslogin.impl

import android.content.Intent
import android.util.SparseArray
import com.newmedia.kingslogin.CallbackManager
import com.newmedia.kingslogin.helper.KingsLogin.KINGSLOGIN_REQUEST_CODE

internal class CallbackManagerImpl : CallbackManager {

    private val callbacks = SparseArray<Callback>()

    fun registerCallback(requestCode: Int, callback: Callback) {
        callbacks.append(requestCode, callback)
    }

    fun unregisterCallback(requestCode: Int) {
        callbacks.remove(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        val callback = callbacks[requestCode]
        return callback != null && callback.onActivityResult(resultCode, data)
    }

    interface Callback {
        fun onActivityResult(resultCode: Int, data: Intent?): Boolean
    }

    enum class RequestCodeOffset(private val offset: Int) {
        Login(0);

        fun requestCode(): Int = KINGSLOGIN_REQUEST_CODE + offset
    }

}