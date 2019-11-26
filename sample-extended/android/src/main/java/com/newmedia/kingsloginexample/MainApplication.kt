package com.newmedia.kingsloginexample

import android.app.Application
import com.newmedia.kingslogin.helper.KingsLogin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KingsLogin.init(this)
    }

}
