package com.newmedia.kingslogin

import com.newmedia.kingslogin.model.KcScopes

interface KingsLoginCallback {

    fun onSuccess(token: String, scopes: KcScopes)

    fun onCancel()

    fun onError(error: KingsLoginException)
}
