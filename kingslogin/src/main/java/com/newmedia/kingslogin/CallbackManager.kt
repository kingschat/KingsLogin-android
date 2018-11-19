package com.newmedia.kingslogin

import android.content.Intent
import com.newmedia.kingslogin.impl.CallbackManagerImpl

interface CallbackManager {

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean

    object Factory {
        fun create(): CallbackManager = CallbackManagerImpl()
    }
}
